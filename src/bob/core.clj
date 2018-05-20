(ns bob.core
  (:require
   [bob.config :as config]
   [bob.db.core :as db]
   [bob.env :as env]
   [bob.executor :as executor]
   [bob.routes.app-routes :as app-routes]
   [bob.env :as env]
   [clojure.tools.logging :as log]
   [compojure.core :refer [routes]]
   [mount.core :as mount]
   [ring.adapter.jetty :as jetty]
   [ring.util.http-response :as response]
   [taoensso.carmine :as car :refer [wcar]])
  (:gen-class))

(defn handler [request]
  (response/ok {:foo (:remote-addr request)}))

(defn app-routes []
  (routes
   (-> #'app-routes/default-routes
       (env/wrap-comp))))

(mount/defstate http-server
  :start
  (jetty/run-jetty
   (app-routes)
   {:port 3000
    :join? false}))

(defn start-app [args]
  (doseq [component (-> args
                        mount/start-with-args
                        :started)]
    (log/info component "started..."))
  (clojure.core.async/thread
    (while (true? true)
      (do
        (db/do-work)
        (Thread/sleep 5000)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (executor/test-exec)
  (start-app args))

