(ns bob.db.core-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [bob.db.core :refer :all]
            [mount.core :as mount]))

(defn init-db [f]
  (doseq [component
          (mount/start #'bob.db.core/*conn*)]
    (log/info component "started..."))
  (f))

(use-fixtures :once init-db)

(deftest test-set-operations
  (testing "test set operations"
    (let [mykey "foo"
          myval "bar"]
      (remove-set mykey)
      (is (nil? (find-set mykey)))
      (save-set mykey myval)
      (is (= myval (find-set mykey)))
      (remove-set mykey)
      (is (nil? (find-set mykey))))))

(deftest test-hash-operations
  (testing "test hash operations"
    (let [mykey "foo"
          mysubkey "bar"
          myval "baz"]
      (remove-hash mykey mysubkey)
      (is (nil? (find-hash mykey mysubkey)))
      (save-hash mykey mysubkey myval)
      (is (= myval (first
                    (find-hash mykey mysubkey))))
      (remove-hash mykey mysubkey)
      (is (nil? (find-hash mykey mysubkey))))))

(deftest test-list-operations
  (testing "test list operations"
    (let [mykey "foo"
          myval1 "bar"
          myval2 "baz"]
      (remove-list mykey)
      (is (= 0 (get-length mykey)))
      (save-list mykey myval1)
      (save-list mykey myval2)
      (is (= 2 (get-length mykey)))
      (remove-list mykey)
      (is (= 1 (get-length mykey)))
      (remove-list mykey)
      (is (= 0 (get-length mykey))))))
