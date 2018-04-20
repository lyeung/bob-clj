(ns bob.db.core
  (:require
   [clojure.tools.logging :as log]
   [environ.core :as environ]
   [taoensso.carmine :as car :refer (wcar)]
   [mount.core :refer [defstate]]))

(defstate ^:dynamic *db*
  :start {:pool {}
          :spec {:uri
                 (environ/env :redis)}})

(defn do-work []
  (wcar *db* (car/set "Nick" "Nack"))
  (log/info (wcar *db* (car/get "Nick"))))
