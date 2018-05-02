(ns bob.db.build-repo
  (:require
   [bob.db.core :as dbcore]))

;; keyname constant
(def keyname "build-repo:")

;; build repo key
(defn build-repo-key [build-repo]
  (->> build-repo
       (:id)
       (str keyname)))

;; id from key
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

;; tuplize
(defn tuplize [coll]
  (loop [result []
         c coll]
    (if (empty? c)
      result
      (recur (conj result
                   {(keyword (first c))
                    (second c)})
             (nthrest c 2)))))

;; find by key
(defn find-by-key [k]
  (let [v (dbcore/find-hash k)]
    (if (not-empty v)
      (apply conj {:id (id-from-key k)}
             (tuplize v)))))

;; remove by key
(defn remove-by-key [k]
  (let [coll (->> k
        (dbcore/find-hash)
        (take-nth 2)
        (concat ["id"]))]
    (loop [sks coll]
      (if (empty? sks)
        nil
        (do
          (dbcore/remove-hash k (first sks))
          (recur (rest sks)))))))
