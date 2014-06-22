(ns landing
  (:use [compojure.core]
        [landing.markdown]
        [ring.util.response :only (redirect)])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [landing.view :as view]))

(defn- make-github-url [user repo path]
  (str "https://raw.githubusercontent.com/" user "/" repo "/master/" path))

(defn landing-page [user repo]
  (let [get-file (partial make-github-url user repo)]
    (try (-> (client/get (get-file "README.md"))
             (:body)
             parse-markdown
             view/landing-page)
    (catch Exception e (view/not-found user repo)))))

(defroutes app-routes
  (route/resources "/" {:root ""})
  (GET "/" [] (view/homepage))
  (POST "/" [repo] (redirect repo))
  (GET "/:user/:repo" [user repo] (landing-page user repo))
  (route/not-found (view/homepage)))

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))
