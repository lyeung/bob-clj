(ns bob.db.build-repo
  (:require [bob.db.core :as dbcore]))

(def keyname "build-repo:")

(defn build-repo-key [build-repo]
  (->> (:id build-repo)
       (str keyname)))

(defn build-repo-subkey-vals [build-repo]
  (list "name" (:name build-repo)
   "url" (:url build-repo)))

(defn build-repo-content [build-repo]
  (flatten (list  (build-repo-key build-repo)
        (build-repo-subkey-vals build-repo))))

(defn save [build-repo]
  (let [args (build-repo-content build-repo)]
    (apply dbcore/save-hash args)))

