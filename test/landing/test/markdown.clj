(ns landing.test.markdown
  (:use [clojure.test]
        [clojure.string :only (join)]
        [hiccup.page :only (html5)]
        [landing.markdown :only (parse-markdown)]))

(def markdown (join "\n" ["# Foo" "blah" "## Bar" "blub"]))

(def html (str "<!DOCTYPE html>\n<html>"
               "<section><h1>Foo</h1><p>blah</p></section>"
               "<section><h2>Bar</h2><p>blub</p></section></html>"))

(deftest foo
  (is (= html (html5 (parse-markdown markdown)))))
