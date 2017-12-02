(ns advent-2016.day-4
    (:require [advent-2016.utils.core :as u]
              [clojure.string :as s]))

(defn ^:private s->letters [letters]
    (->> letters
        (group-by identity)
        (map (fn [[k v]] [k (count v)]))
        (into {})))

(defn ^:private s->room [line]
    (let [[_ letters sector-id checksum] (re-find (re-matcher #"(\D+)(\d+)\[(.*)\]" line))]
        {:letters (s->letters (s/replace letters #"-" ""))
         :sector-id (Integer/parseInt sector-id)
         :checksum (seq checksum)}))

(defn ^:private real-room? [{:keys [letters checksum]}]
    (->> letters
        (sort-by (juxt (comp  (partial * -1) second) first))
        (take (count checksum))
        (map first)
        (vec)
        (= checksum)))

;; 158835
(defn step-1 []
    (->> (u/read-file 4 #"\n")
        (map s->room)
        (filter real-room?)
        (map :sector-id)
        (reduce +)))
