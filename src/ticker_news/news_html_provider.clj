(ns ticker-news.news-html-provider
  (:require [hiccup.core :as h]
            [ticker-news.yahoo-news-provider :as y]
            [ticker-news.story :as s]))

(defn ^:private story->div-element
  [story]
  [:div {:class "span4"} 
   [:p (:title story) [:br] 
    [:a {:href (:link story)} (:link story)] [:br]
    (s/story->date story)]])

(defn get-news-html
  "Get latest news for a ticker symbol"
  [ticker]
  (let [stories (y/get-ticker-stories ticker)
        _       (println (format "found %s stories for %s" (count stories) ticker))]
    (if (empty? stories)
      (h/html [:html (format "No news found for the ticker symbol \"%s\"" ticker)])
      (h/html [:html 
               [:head
                [:title (format "News for %s" ticker)]] 
               [:body 
                [:div {:class "container"}
                 [:div {:class "row"}
                  (for [story stories] 
                    (story->div-element story))]]]]))))

(defn get-form-page
  []
  (h/html [:html
           [:body
            [:form {:method "post"} "Enter ticker symbol " 
             [:input {:type "text" :name "ticker"}]
             [:input {:type "submit"}]]]]))