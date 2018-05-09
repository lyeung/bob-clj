(ns bob.handlers.build-repo-handler
  (:require
   [bob.db.build-repo :as build-repo]
   [ring.util.http-response :as response]))

(defn save-build-repo [req]
  (let [payload (:params req)]
    (build-repo/save payload)
    (response/ok)))

(defn get-build-repo [id]
  (response/ok (build-repo/find-by-key
                (str build-repo/keyname id))))

