(ns littleli.amentum
  (:import (io.javalin Javalin)
           (io.javalin.http Context Handler))
  (:gen-class))

;; construct handler
(defn handler [func]
  (reify Handler
    (^void handle [_ ^Context ctx]
      (func ctx))))

;; initialize the Javalin server
(defn run-server []
  (-> (Javalin/create)
      (.start 7070)))

;; run the app and 
(defn -main []
  (doto (run-server)
    (.get "/"
          (handler (fn [ctx] (.result ctx "Hello there!"))))
    (.get "/hello/{name}"
          (handler (fn [ctx] (.result ctx (str "Hello: " (.pathParam ctx "name"))))))
    (.get "/path/*"
          (handler (fn [ctx] (.result ctx (str "You're here because " (.path ctx) " matches " (.matchedPath ctx))))))))

(-main)
