(ns landing.view
  (:use [hiccup.page :only (html5 include-js include-css)]))

(defn landing-page [html]
  (html5
    [:head [:title ""]
           (include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css")
           (include-css "/css/solarized-dark.css")
           (include-css "/css/index.css")
           (include-js "//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js")
           (include-js "/js/landing.js")]
    [:body html]))
