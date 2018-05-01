(ns bob.db.build-repo
  (:require
   [bob.db.core :as dbcore]))

;; keyname constant
(def keyname "build-repo:")

;; build-repo-key specs and fn
(defn build-repo-key [build-repo]
  (->> build-repo
       (:id)
       (str keyname)))

;; id-from-key
(defn id-from-key [k]
  (let [result (re-matches #"\S+:(.+)" k)]
    (if (nil? result)
      result
      (second result))))

(defn build-repo-subkey-vals [build-repo]
  (list "name" (:name build-repo)
   "url" (:url build-repo)))

(defn build-repo-content [build-repo]
  (flatten (list (build-repo-key build-repo)
        (build-repo-subkey-vals build-repo))))

(defn save [build-repo]
  (let [args (build-repo-content build-repo)]
    (apply dbcore/save-hash args)))

;; tuplize specs
(defn tuplize [coll]
  (loop [result []
         c coll]
    (if (empty? c)
      result
      (recur (conj result
                   {(keyword (first c))
                    (second c)})
             (nthrest c 2)))))

;; find specs
(defn find-by-key [k]
  (let [v (dbcore/find-hash k)]
    (apply conj {:id (id-from-key k)}
          (tuplize v))))
