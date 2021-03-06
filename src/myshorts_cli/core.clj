(ns myshorts-cli.core
  (:require [clojure.string :as str]
            [cheshire.core :refer :all]
            [clojure.java.io :refer :all]
            [clojure.pprint :refer [print-table]]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))



(defn gen-uuid
  []
  (java.util.UUID/randomUUID))

(defn load-shortcuts
  [filename])

(defn write-shortcut
  [args])

(def saved-file "shortcuts.json")

(defn list-shortcuts
  []
  (when (.exists (as-file saved-file))
    (println "Reading file")
    (print-table [:short :desc :tags] (parse-string (slurp saved-file) true))))

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
 ([]
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
                          :desc desc
                          :id (gen-uuid)
                          :short shortcut
                          :tags tags))))))
 ([shortcut desc tags]
  (store-shortcut (hash-map
                   :desc desc
                   :id (gen-uuid)
                   :short shortcut
                   :tags (if (empty? tags)
                            " "
                          tags)))))


(defn create
  [shortcut desc & tags]
  (str "short "
       shortcut "desc " desc "tags " tags))


;; (defn list-shortcuts
;;   []
;;   (str (not (nil? (read-shortcuts-file)))))

(defn read-shortcuts-file
  []
  (parse-string (slurp saved-file) true))

(defn search-shortcuts
  [tag]
  (print-table [:short :desc :tags]
               (filter #(.contains (:tags %) tag)
                       (read-shortcuts-file))))

(defn delete-shortcut
  [args]
  (let [shorts (read-shortcuts-file)]
    (if (empty?
         (filter (comp #{args} :short)
                 shorts))
      (println "Shortcut not found")
      (do
        (let [updated-shorts
              (filter
               #(not= (:short %) args)
               shorts)]
          (spit saved-file
                (generate-string updated-shorts
                                 {:pretty true}))
          (println updated-shorts))
        (println "Shortcut deleted")))))



(def cli-options
  [["-h" "--help" "Print HELP"]
   ["-a" "--add-shortcut" "Add short [shortcut] [Description] [Tags]"]
   ["-d" "--delete" "Delete shortcut [shortcut]"]
   ["-s" "--search" "Search for shortcuts [tag]"]
   ["-l" "--list" "List all shortcuts"]])

(defn -main
  [& args]
  (let [{:keys [options arguments]} (parse-opts args cli-options)]
    (cond
      (:help options) (println options)
      (:add-shortcut options)
      (add-shortcut (first arguments)
                    (second arguments)
                    (drop 2 arguments))
      (:delete options)
      (delete-shortcut (first arguments))
      (:search options) (search-shortcuts (first arguments))
      (:list options) (list-shortcuts)))
  )
