(ns bob.routes.app-routes-test
  (:require [bob.core :as core]
            [bob.db.core :as dbcore]
            [bob.db.init-db-fixture :as db-fixture]
            [clojure.data.json :as json]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(use-fixtures :once db-fixture/init-db)

(deftest build-repo-routes
  (let [payload {:id "proj1"
                 :name "foo"
                 :url "baz"}]
        (testing "post payload"
          (dbcore/remove-hash "build-repo:proj1"
                              "name")
          (dbcore/remove-hash "build-repo:proj1"
                              "url")
          (let [req (->
                     (mock/request
                      :post "/build-repo")
                     (mock/json-body payload))
                resp ((core/app-routes) req)]
            (is (= 200 (:status resp)))
            (is (nil? (:body resp)))))
        (testing "get by id"
          (let [req (mock/request
                     :get "/build-repo/proj1")
                resp ((core/app-routes) req)
                body (slurp (:body resp))]
            (println "resp:" resp)
            (println "body:" body)
            (is (= payload
                   (json/read-str body
                                  :key-fn keyword)))))))
