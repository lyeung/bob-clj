(ns bob.routes.app-routes
  (:require
   [bob.handlers.build-repo-handler
    :as build-repo-handler]
   [bob.handlers.exec-handler
    :as exec-handler]
   [compojure.core
    :refer [context defroutes GET POST]]))

;; build-repo routes
(defn build-repo-routes []
  (context
   "/build-repo" []
   (POST "/" request
         (build-repo-handler/save-build-repo
          request))
   (GET "/:id" [id]
        (build-repo-handler/get-build-repo
         id))))

;; exec subroutes
(defn exec-subroutes []
  (defroutes exec-subroutes
    (context "/:build-spec-id" [build-spec-id]
             (POST "/" []
                   (exec-handler/exec-build
                    build-spec-id))
             (GET "/:build-num" [build-num]
                  (exec-handler/get-build
                   build-spec-id build-num)))))

;; exec routes
(defn exec-routes []
  (context
   "/exec" []
   (exec-subroutes)))

(defroutes default-routes
  (build-repo-routes)
  (exec-routes))
