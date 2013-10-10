(ns ticker-news.model.aggregator
  (:require [ticker-news.providers.provider :as provider]))

(comment
  "This is a funnel for collecting stories from all the registered sources and do any deduping/filtering/sorting
   if needed")

(defn get-aggregated-ticker-news
  [ticker]
  (let [stories        (apply concat (map (partial provider/get-news ticker)
                                          provider/registered-sources))
        uniq-stories   (map #(first %)
                            (vals (group-by :link stories)))
        sorted-stories (reverse (sort-by :timestamp uniq-stories))
        _              (println (format "returning %d stories for %s" (count sorted-stories) ticker))]
    sorted-stories))