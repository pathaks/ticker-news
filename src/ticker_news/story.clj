(ns ticker-news.story)

(comment
  ;Story data is represented as a map. Required fields are title, link and timestamp. Example,
  {:title "Foo" 
   :link "http://foo.bar.com"
   :timestamp 1234})

(defn valid-story?
  [{:keys [title link timestamp] :as story}]
 {:pre [title link timestamp]}
 (if (every? #(not (clojure.string/blank? %)) [title link])
   true
   false))

(defn story->date
  [story]
  (-> (java.util.Date. (:timestamp story))
    (.toString)))