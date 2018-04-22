(ns bob.middleware.default
  (:require
   [ring.middleware.format :refer [wrap-restful-format]]))

(defn wrap-formats [handler]
  (wrap-restful-format
   handler
   {:formats [:json-kw :transit-json :transit-msgpack]}))

