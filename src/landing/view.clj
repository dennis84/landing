(ns landing.view
  (:use [hiccup.page :only (html5 include-js include-css)]))

(defn include-bootstrap-css []
  (include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"))

(defn landing-page [html]
  (html5
    [:head [:title ""]
           (include-bootstrap-css)
           (include-css "/css/solarized-dark.css")
           (include-css "/css/index.css")
           (include-js "//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js")
           (include-js "/js/landing.js")]
    [:body html]))

(defn homepage []
  (html5
    [:head [:title ""]
           (include-bootstrap-css)
           (include-css "/css/index.css")]
    [:body
      [:section
        [:div {:class "container"}
          [:h1 "Landing"]
          [:p "Enter a GitHub username/repo"]
          [:form {:action "/" :method "post" :class "form-inline"}
            [:input {:type "text" :name "repo" :class "form-control input-lg"}]
            [:button {:type "submit" :class "btn btn-primary btn-lg"} "Show"]
          ]]]]))

(defn not-found [user repo] 
  (html5
    [:head [:title ""]]
    [:body "Not found"]))
