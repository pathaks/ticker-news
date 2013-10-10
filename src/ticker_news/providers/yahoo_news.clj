(ns ticker-news.providers.yahoo-news
  (:require [clojure.xml :as xml]
            [clojure.core.memoize :as memo]
            [ticker-news.model.story :as story]
            [ticker-news.providers.util :as util]))

; Yahoo! ticker news provider implementation
; API details can be found here - http://developer.yahoo.com/finance/company.html

(def source-name "Yahoo! News")

(defn ^:private ticker-data->items
  [ticker-data]
  (let [channel-content (-> (filter #(= (:tag %) :channel) (:content ticker-data)) 
                          first
                          :content)]
    (filter #(= (:tag %) :item) channel-content)))

(defn ^:private item->story
  [item]
  (let [content      (:content item)
        title        (-> (filter #(= (:tag %) :title) content) first :content first)
        link         (-> (filter #(= (:tag %) :link) content) first :content first)
        pub-date     (-> (filter #(= (:tag %) :pubDate) content) first :content first)
        timestamp    (if pub-date (-> (java.text.SimpleDateFormat. "EEE, d MMM yyyy H:m:s z" java.util.Locale/ENGLISH) 
                                    (.parse pub-date) 
                                    (.getTime))
                       nil)
        link-idx     (if link (-> link (.indexOf "*"))) ; actual article link is after *
        article-link (if (and link (> link-idx 0)) 
                       (-> link (.substring (+ link-idx 1)))
                       link)]
    {:title title :link article-link :timestamp timestamp}))

(defn get-stories
  [ticker]
  (let [url         (format "http://finance.yahoo.com/rss/headline?s=%s" ticker)
        ticker-data (util/get-url-xml-data-memoized url)
        items       (ticker-data->items ticker-data)]
    (filter #(story/valid-story? %)
            (map item->story items))))