(ns bob.handlers.exec-handler
  (:require
   [bob.executor :as executor]
   [ring.util.http-response :as response]))

(defn exec-build [build-spec-id]
  (executor/test-exec)
  (response/ok {:message "exec build"}))

(defn get-build [build-spec-id build-num]
  (response/ok {:message "get build"}))
