(ns bob.db.build-repo-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [bob.db.core :as dbcore]
            [bob.db.build-repo :refer :all]
            [bob.db.init-db-fixture :as db-fixture]))

(use-fixtures :once db-fixture/init-db)

(def repoUrl "https://github.com/lyeung/bob-clj")

(deftest test-build-repo
  (let [build-repo
        {:id "hash123"
         :name "foo"
         :url repoUrl}]
    (testing "build-repo key"
      (is (= "build-repo:hash123"
             (build-repo-key build-repo))))
    (testing "build-repo subkey vals"
      (is (= ["name"
              "foo"
              "url"
              repoUrl]
             (build-repo-subkey-vals build-repo))))
    (testing "build-repo content"
      (is (= (list "build-repo:hash123"
              "name"
              "foo"
              "url"
              repoUrl)
              (build-repo-content build-repo))))
    (testing "save"
      (dbcore/remove-hash "build-repo:hash123"
                          "name")
      (dbcore/remove-hash "build-repo:hash123"
                          "url")
      (is (= (nil?
              (dbcore/find-hash
               "build-repo:hash123"
               "name"))))
      (save build-repo)
      (is (= ["name"
              "foo"
              "url"
              repoUrl]
             (dbcore/find-hash
              "build-repo:hash123"))))))
