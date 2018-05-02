(ns bob.db.init-db-fixture
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [bob.db.core :refer :all]
            [mount.core :as mount]))

(defn init-db [f]
  (doseq [component
          (mount/start #'bob.db.core/*conn*)]
    (log/info component "started..."))
  (f))

