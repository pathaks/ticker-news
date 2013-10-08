(ns ticker-news.model.story)

(comment
  ;Story data is represented as a map. Required fields are title, link and timestamp. Example,
  {:title "Foo" 
   :link "http://foo.bar.com"
   :timestamp 1234
   :source "foo-news"})

(defn valid-story?
  [{:keys [title link timestamp source] :as story}]
 (if (every? #(not (clojure.string/blank? %)) [title link])
   true
   false))

(defn story->date
  [story]
  (str (-> (java.util.Date. (:timestamp story)))))