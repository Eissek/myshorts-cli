(ns myshorts-cli.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "hi"))

(defn enter-shortcut
  []
  (println "Enter Shortcut:")
  (let [shortcut (read-line)]
    (println shortcut)
    (println "Enter Shortcut Description:")
    (let [desc (read-line)]
      (str desc)
      (println "Enter tags:")
      (let [tags (read-line)]
        (if (empty? tags)
          (println "No tags added" )
          (str/split tags #"\s"))
        (str "Added " shortcut " " desc)))))

