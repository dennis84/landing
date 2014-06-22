(ns landing.templating)

(defn replace-params [text params]
  (clojure.string/replace text 
    #"\{(\w+)}"
    (fn [[_ groups]]
      ((keyword groups) params))))
