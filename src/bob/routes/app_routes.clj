(ns bob.routes.app-routes
  (:require
   [bob.handlers.exec-handler :as exec-handler]
   [compojure.core :refer [context defroutes GET POST]]))

(defroutes default-routes
  (context
   "/exec" []
   (defroutes exec-routes
     (context "/:build-spec-id" [build-spec-id]
              (POST "/" []
                    (exec-handler/exec-build build-spec-id))
              (GET "/:build-num" [build-num]
                   (exec-handler/get-build build-spec-id build-num))))))

