(ns ticker-news.providers.yahoo-news
  (:require [clojure.xml :as xml]
            [clojure.core.memoize :as memo]
            [ticker-news.model.story :as story]))

;Yahoo! ticker news provider implementation

(def source-name "Yahoo! News")

(defn ^:private get-ticker-data
  [ticker]
  (let [_          (println (format "fetching data for %s" ticker))
        ticker-url (format "http://finance.yahoo.com/rss/headline?s=%s" ticker)]
   (xml/parse ticker-url)))

(def get-ticker-data-memoized (memo/ttl get-ticker-data :ttl/threshold 600000)) ; cache data for 600s

(defn ^:private ticker-data->items
  [ticker-data]
  (let [channel-content (-> (filter #(= (:tag %) :channel) (:content ticker-data)) 
                          first
                          :content)]
    (filter #(= (:tag %) :item) channel-content)))

(defn ^:private item->story
  [item]
  (let [content   (:content item)
        title     (-> (filter #(= (:tag %) :title) content) first :content first)
        link      (-> (filter #(= (:tag %) :link) content) first :content first)
        pub-date  (-> (filter #(= (:tag %) :pubDate) content) first :content first)
        timestamp (if pub-date (-> (java.text.SimpleDateFormat. "EEE, d MMM yyyy H:m:s z" java.util.Locale/ENGLISH) 
                                 (.parse pub-date) 
                                 (.getTime))
                    nil)]
    {:title title :link link :timestamp timestamp}))

(defn get-ticker-yahoo-news
  [ticker]
  (let [ticker-data (get-ticker-data-memoized ticker)
        items       (ticker-data->items ticker-data)]
    (filter #(story/valid-story? %)
            (map item->story items))))