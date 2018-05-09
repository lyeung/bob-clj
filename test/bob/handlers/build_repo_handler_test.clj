(ns bob.handlers.build-repo-handler-test
  (:require
   [bob.db.build-repo :as build-repo]
   [bob.handlers.build-repo-handler :as h]
   [bob.mock :refer [defmocktest
                     mocking
                     stubbing
                     verify-call-times-for
                     verify-first-call-args-for
                     verify-nth-call-args-for]]
   [clojure.test :refer :all]
   [ring.util.http-response :as response]))


(defmocktest save-build-repo
  (testing "save build-repo"
    (mocking [build-repo/save]
             (let [params {:id "foo"
                           :name "bar"
                           :url "baz"}
                   req {:params params}
                   result (h/save-build-repo req)]
               (is (= (response/ok) result))
               (verify-call-times-for
                build-repo/save 1)
               (verify-first-call-args-for
                build-repo/save {:id "foo"
                                 :name "bar"
                                 :url "baz"})))))

(defmocktest get-build-repo
  (testing "get build-repo"
    (stubbing [build-repo/find-by-key
               {:id "1"
                :name "bar"
                :url "baz"}]
              (let [resp (h/get-build-repo "build-repo:1")]
                (is (= 200 (:status resp)))
                (is (= {:id "1"
                        :name "bar"
                        :url "baz"}
                       (:body resp)))))))
