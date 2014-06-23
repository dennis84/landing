(ns landing.test.templating
  (:use [clojure.test]
        [landing.templating]))

(deftest replace-params-A-test
  (is (= "foo bar baz" (replace-params "foo {bar} baz" {:bar "bar"}))))

(deftest replace-params-B-test
  (is (= "foo {bar} baz" (replace-params "foo {bar} baz" {:foo "foo"}))))
