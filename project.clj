(defproject ticker-news "0.1.0-SNAPSHOT"
  :description "Get news for a ticker symbol"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [org.clojure/core.memoize "0.5.6"]]
  :plugins [[lein-ring "0.8.5"]
            [lein-beanstalk "0.2.7"]]
  :ring {:handler ticker-news.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
