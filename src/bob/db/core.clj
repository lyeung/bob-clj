(ns bob.db.core
  (:require
   [clojure.tools.logging :as log]
   [environ.core :as environ]
   [mount.core :refer [defstate]]
   [taoensso.carmine :as car :refer (wcar)]))

(defstate ^:dynamic *conn*
  :start {:pool {}
          :spec {:uri
                 (environ/env :redis)}})
;; save as set
(defn save-set [k v]
  (wcar *conn* (car/set k v)))

;; find set by key
(defn find-set [k]
  (wcar *conn* (car/get k)))

(defn remove-set [k]
  (wcar *conn* (car/del k)))

;; save hash
(defn save-hash [k sk v]
  (wcar *conn* (car/hset k sk v)))

;; find hash by key and subkey
(defn find-hash [k sk]
  (wcar *conn* (car/hget k sk)))

(defn remove-hash [k sk]
  (wcar *conn* (car/hdel k sk)))

(defn save-list [k v]
  (wcar *conn* (car/rpush k v)))

;; find list
;; [] returns the entire list for key k
;; [k i] returns an element from key k at index i
;; [k start end] returns a list for key k starting
;;   from index start and index end inclusively
(defn find-list
  ([k]
   (wcar *conn* (car/lrange k 0 -1)))
  ([k i]
   (wcar *conn* (car/lindex k i)))
  ([k start end]
   (wcar *conn* (car/lrange k start end))))

(defn get-length [k]
  (wcar *conn* (car/llen k)))

(defn remove-list [k]
  (wcar *conn* (car/lpop k)))

(defn do-work []
  (save-set "Nick" "Nack")
  (println (find-set "Nick")))

