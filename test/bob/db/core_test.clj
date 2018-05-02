(ns bob.db.core-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [bob.db.core :refer :all]
            [bob.db.init-db-fixture :as db-fixture]))

(use-fixtures :once db-fixture/init-db)

(deftest test-set-operations
  (testing "set operations"
    (let [mykey "foo"
          myval "bar"]
      (remove-set mykey)
      (is (nil? (find-set mykey)))
      (save-set mykey myval)
      (is (= myval (find-set mykey)))
      (remove-set mykey)
      (is (nil? (find-set mykey))))))

(deftest test-hash-operations
  (testing "hash operations"
    (let [mykey "foo"
          mysubkey1 "bar"
          myval1 "bar-val"
          mysubkey2 "baz"
          myval2 "baz-val"]
      (remove-hash mykey mysubkey1)
      (is (= 0 (hash-length mykey)))
      (save-hash mykey mysubkey1 myval1
                 mysubkey2 myval2)
      (is (= myval1 (first
                    (find-hash mykey mysubkey1))))
      (remove-hash mykey mysubkey1)
      (is (= 1 (hash-length mykey)))
      (remove-hash mykey mysubkey2)
      (is (= 0 (hash-length mykey))))))

(deftest test-list-operations
  (testing "list operations"
    (let [mykey "foo"
          myval1 "bar"
          myval2 "baz"]
      (remove-list mykey)
      (is (= 0 (get-length mykey)))
      (save-list mykey myval1)
      (is (= 1 (get-length mykey)))
      (save-list mykey myval2)
      (is (= 2 (get-length mykey)))
      (remove-list mykey)
      (is (= 1 (get-length mykey)))
      (remove-list mykey)
      (is (= 0 (get-length mykey))))))
