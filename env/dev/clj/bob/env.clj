(ns bob.env
  (:require [bob.middleware.default :as middleware]
            [ring.middleware.reload :refer [wrap-reload]]))

(def wrap-comp
  (comp wrap-reload
        middleware/wrap-formats))

