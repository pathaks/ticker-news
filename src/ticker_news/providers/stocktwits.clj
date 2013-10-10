(ns ticker-news.providers.stocktwits
  (:require [clojure.core.memoize :as memo]
            [ticker-news.model.story :as story]
            [ticker-news.providers.util :as util]
            [clojure.data.json :as json]))

; API details can be found here - http://stocktwits.com/developers/docs/api#streams-symbol-docs

(def source-name "StockTwits")

(defn ^:private link->story
  [link-map]
  (let [created_at (get link-map "created_at")
        timestamp  (if created_at (-> (java.text.SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss" java.util.Locale/ENGLISH) 
                                   (.parse created_at) 
                                   (.getTime))
                    nil)]
    {:title (get link-map "title") :link (get link-map "url") :timestamp timestamp}))

(defn ^:private data->stories
  [data]
  (map #(link->story (-> % (get "links") first)) (get data "messages")))

(defn get-stories
  [ticker]
  (try
    (let [url        (format "https://api.stocktwits.com/api/2/streams/symbol/%s.json?filter=links" ticker)
          data-json  (json/read-str (util/get-url-data-memoized url))
          stories    (data->stories data-json)]
      (filter #(story/valid-story? %) stories))
    (catch Exception e (let [_ (println (format "Exception encountered %s" e))] nil))))