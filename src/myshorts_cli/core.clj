(ns myshorts-cli.core
  (:require [clojure.string :as str]
            [cheshire.core :refer :all]
            [clojure.java.io :refer :all])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "hi"))

(defn gen-uuid
  []
  (java.util.UUID/randomUUID))

(defn load-shortcuts
  [filename])

(defn write-shortcut
  [args])

(def saved-file "shortcuts.json")

(defn store-shortcut
  [args]
  (if (.exists (as-file saved-file))
    (do
      (let [shortcuts
            (parse-string (slurp saved-file) true)]
        (spit saved-file
              (generate-string
               (conj shortcuts args) {:pretty true})))
      (println "Shortcut successfully stored "))
    (do
      (spit saved-file (generate-string [args]))
      (println "Saved to new file")))
  (println "DONE"))


 (defn add-shortcut
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
        (store-shortcut (hash-map
                         :id (gen-uuid)
                         :short shortcut
                         :tags tags)))))


(defn list-shortcuts
  [])


(defn search-shortcuts
  []))
