(ns ticker-news.test.model.story
  (:use clojure.test
        ticker-news.model.story))

(deftest test-valid-story?
  (testing "valid-story?"
           (let [valid-story {:title "foo" :link "http://foo.bar.com" :timestamp 1234}
                 invalid-story (dissoc valid-story :link)
                 bad-story {:title "" :link "http://foo.bar.com" :timestamp 1234}]
             (is (valid-story? valid-story))
             (is (not (valid-story? bad-story)))
             (is (not (valid-story? invalid-story))))))