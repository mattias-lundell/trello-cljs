(defproject trello-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.7.1"]
                 [markdown-clj "0.9.55"]
                 [cljs-http "0.1.18"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "trello-clj"
              :source-paths ["src"]
              :compiler {
                :output-to "trello_clj.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
