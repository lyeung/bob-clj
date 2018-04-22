(ns bob.db.core-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [bob.db.core :refer :all]
            [mount.core :as mount]))

(defn init-db [f]
  (doseq [component
          (mount/start #'bob.db.core/*conn*)]
    (log/info component "started...")))

(use-fixtures :once init-db)

(deftest test-db-operation
  (testing "test set operation"
    (let [mykey "foo"
          myval "bar"]
      (remove-set mykey)
      (is (nil? (find-set mykey)))
      (save-set mykey myval)
      (is (= myval (find-set mykey)))
      (remove-set mykey)))
  (testing "test hash operation"
    (let [mykey "foo"
          mysubkey "bar"
          myval "baz"]
      (remove-hash mykey mysubkey)
      (is (nil? (find-hash mykey mysubkey)))
      (save-hash mykey mysubkey myval)
      (is (= myval (find-hash mykey mysubkey)))
      (remove-hash mykey mysubkey)))
  (testing "test list operation"
    (let [mykey "foo"
          myval1 "bar"
          myval2 "baz"]
      (letfn [(remove-all [k]
                (let [len (get-length k)]
                  (for [i (range len)
                    :when (not= len i)]
                    (remove-list k))))]
              (remove-all mykey)
              (is (= 0 (get-length mykey)))
              (save-list mykey myval1)
              (save-list mykey myval2)
              (is (= 2 (get-length mykey)))
              (remove-all mykey))
      (is (= 0 (get-length mykey))))))
