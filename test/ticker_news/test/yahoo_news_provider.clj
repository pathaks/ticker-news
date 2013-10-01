(ns ticker-news.test.yahoo-news-provider
  (:use clojure.test
        ticker-news.yahoo-news-provider))

(def data {:content [{:tag :channel 
                      :content [{:tag :item :content [{:tag :title :content ["foo"]}
                                                      {:tag :link :content ["http://foo.bar.com"]}
                                                      {:tag :description :content ["foo bar"]}
                                                      {:tag :pubDate :content ["Mon, 30 Sep 2013 20:01:00 GMT"]}
                                                      ]}]}]})

(deftest test-get-ticker-stories
  (testing "get ticker stories"
           (with-redefs [get-ticker-data-memoized (constantly data)]
             (is (= {:title "foo" :link "http://foo.bar.com" :timestamp 1380571260000} 
                    (first (get-ticker-stories "foo")))))))