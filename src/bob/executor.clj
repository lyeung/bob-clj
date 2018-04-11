(ns bob.executor
  (:import (java.util.concurrent
            LinkedBlockingQueue
            TimeUnit
            ThreadPoolExecutor)
            (java.io BufferedInputStream)
            (java.nio.charset StandardCharsets))
  (:require [clojure.java.io :as io]))

;; thread-pool related values
(def core-pool 2)
(def max-pool-size 4)
(def keep-alive-time 30)
(def bounded-queue-length 10)

;; create a default thread pool executor
(defn create-executor []
  (ThreadPoolExecutor.
   core-pool max-pool-size keep-alive-time TimeUnit/SECONDS
   (LinkedBlockingQueue. bounded-queue-length)))

;; define a default executor
(def build-executor (create-executor))

;; execute shell command
(defn exec [command]
  (.exec (Runtime/getRuntime) command))

;; store the content string into in-mem buffer
(defn store-in-mem [in-mem-buf
                    content]
  (reset! in-mem-buf content))

;; store the content string into file
(defn store-file [file-path
                  content]
  (with-open [w (io/writer file-path
                           :append true)]
    (.write w content))
  content)

;; define in-memory storage
(def in-mem (atom ""))

;; define filepath storage
(def file-path "/tmp/bob.log")

;; new string from bytes with up to read-size length
(defn new-string [buf read-size]
  (String. buf, 0 read-size StandardCharsets/UTF_8))

;; store the process output buffer
;; into file and mem
(defn store-buffer [buf read-size]
  (let [content (new-string buf read-size)]
    (->> content
         (store-file file-path)
         (store-in-mem in-mem))))

;; process outputstream buffer related values
(def process-out-buffer-size 2048)

;; main process output redirection
(defn redir-output [process]
  (with-open [instream
              (BufferedInputStream.
               (.getInputStream process))]
    (let [flag (atom true)]
      (while (true? @flag)
        (let [buf (make-array Byte/TYPE process-out-buffer-size)
              read-size (.read instream buf)]
          (if (= read-size -1)
              (swap! flag not)
              (store-buffer buf read-size)
         ))))))

;; create a callable task
(defn create-task [command]
  (reify Callable
    (call [this]
      (try
        (let [process (exec command)]
          (redir-output process)
          (.waitFor process))))))

;; submit a command
(defn submit-command [command]
  (-> build-executor
      (.submit (create-task command))))

(defn test-exec []
  (submit-command "ls /")
  (.shutdown build-executor)
  (Thread/sleep 5000)
  (println "in-mem" @in-mem))

