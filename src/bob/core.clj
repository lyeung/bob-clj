(ns bob.core
  (:require
   [bob.executor :as executor]
   [bob.routes.app-routes :as app-routes]
   [compojure.core :refer [routes]]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.format :refer [wrap-restful-format]]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.util.http-response :as response])
  (:gen-class))

(defn handler [request]
  (response/ok {:foo (:remote-addr request)}))

(defn wrap-formats [handler]
  (wrap-restful-format
   handler
   {:formats [:json-kw :transit-json :transit-msgpack]}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (executor/test-exec)
  (jetty/run-jetty
   (-> app-routes/default-routes
       wrap-reload
       wrap-formats)
   {:port 3000
    :join? false}))

