(ns ticker-news.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ticker-news.news-html-provider :as nhp]))

(defroutes app-routes
  (GET "/" [] (nhp/get-form-page))
  (POST "/" {params :params} (nhp/get-news-html {:ticker params}))
  (GET "/news/:tckr" [tckr] (nhp/get-news-html tckr))
  (route/resources "/" "/news")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
