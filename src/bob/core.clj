(ns bob.core
  (:require
   [bob.env :as env]
   [bob.executor :as executor]
   [bob.routes.app-routes :as app-routes]
   [bob.env :as env]
   [compojure.core :refer [routes]]
   [ring.adapter.jetty :as jetty]
   [ring.util.http-response :as response])
  (:gen-class))

(defn handler [request]
  (response/ok {:foo (:remote-addr request)}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (executor/test-exec)
  (jetty/run-jetty
   (-> app-routes/default-routes
       env/wrap-comp)
   {:port 3000
    :join? false}))

