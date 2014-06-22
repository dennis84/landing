(ns landing
  (:use [compojure.core]
        [landing.markdown]
        [ring.util.response :only (redirect)])
  (:require [clj-http.client :as client]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [landing.view :as view]
            [landing.templating :as tpl]))

(defn- make-github-url [user repo path]
  (str "https://raw.githubusercontent.com/" user "/" repo "/master/" path))

(defn- landing-page [user repo]
  (try
    (let [theme (:body (client/get (make-github-url "dennis84" "landing-theme" "index.html")))
          markdown (:body (client/get (make-github-url user repo "README.md")))
          html (parse-markdown markdown)]
      (tpl/replace-params theme {:title "" :body html}))
    (catch Exception e (view/not-found user repo))))

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
