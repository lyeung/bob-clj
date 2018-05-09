;; base from Clojure In Action 2ed Chapter 10.2
(ns bob.mock
  (:require [clojure.test :refer :all]))

(def ^:dynamic *mock-calls*)

(defn clear-calls []
  (reset! *mock-calls* {}))

(defn stub-fn [the-fn ret-value]
  (swap! *mock-calls* assoc the-fn [])
  (fn [& args]
    (swap! *mock-calls* update-in [the-fn] conj args)
  ret-value))

(defmacro stubbing [stub-forms & body]
  (let [stub-pairs (partition 2 stub-forms)
        real-fns (map first stub-pairs)
        returns (map last stub-pairs)
        stub-fns (map #(list `stub-fn %1 %2)
                      real-fns returns)]
    `(with-redefs [~@(interleave
                      real-fns stub-fns)]
       ~@body)))

(defn mock-fn [the-fn]
  (stub-fn the-fn nil))

(defmacro mocking [fn-names & body]
  (let [mocks (map #(list `mock-fn
                          (keyword %))
                   fn-names)]
    `(with-redefs [~@(interleave fn-names mocks)]
       ~@body)))

(defmacro verify-call-times-for
  [fn-name number]
  `(is (= ~number
          (count (@*mock-calls* ~(keyword fn-name))))))

(defmacro verify-nth-call-args-for
  [n fn-name & args]
  `(is (= '~args (nth (@*mock-calls*
                       ~(keyword fn-name))
                      (dec ~n)))))

(defmacro verify-first-call-args-for
  [fn-name & args]
  `(verify-nth-call-args-for 1 ~fn-name ~@args))

(defmacro defmocktest [test-name & body]
  `(deftest ~test-name
     (binding [*mock-calls* (atom {})]
       (do ~@body))))
