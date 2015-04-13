(ns myshorts-cli.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "hi"))

 (defn mymy
  []
  (println "Enter:")
   (let [shortcut
         (loop [x (read-line)]
           (if (empty? x)
             (do (println "Enter shortcut")
                     (recur (read-line)))
             (str x)))]
    (println "Enter shortcut Description")
    (let [desc
          (loop [x (read-line)]
            (if (empty? x)
              (do
                (println "Enter a shortcut description ")
                (recur (read-line)))
              (str x)))]
      (println "Enter tags(Optional):")
      (let [tags (read-line)]
        (if (empty? tags)
              (println "No tags entered.")
              (println tags))
        (str "Added " shortcut " " desc)))))

