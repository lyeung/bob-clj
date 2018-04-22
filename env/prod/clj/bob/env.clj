(ns bob.env
  (:require [bob.middleware.default :as middleware]))

(def wrap-comp
  (comp middleware/wrap-formats))

