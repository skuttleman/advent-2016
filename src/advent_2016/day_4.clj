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
        {:encrypted-name letters
         :letters (s->letters (s/replace letters #"-" ""))
         :sector-id (Integer/parseInt sector-id)
         :checksum (seq checksum)}))

(defn ^:private real-room? [{:keys [letters checksum]}]
    (->> letters
        (sort-by (juxt (comp  (partial * -1) second) first))
        (take (count checksum))
        (map first)
        (vec)
        (= checksum)))

(defn ^:private rotate-letter [times letter]
    (if (= \- letter)
        " "
        (->> u/letter-seq
            (drop-while (partial not= letter))
            (drop times)
            (first))))

(defn ^:private room->name [{:keys [encrypted-name sector-id] :as room}]
    (->> encrypted-name
        (map rotate-letter (repeat (mod sector-id 26)))
        (apply str)
        (s/trim)
        (assoc room :name)))

(defn ^:private prep []
    (->> (u/read-file 4 #"\n")
        (map s->room)
        (filter real-room?)))

;; 158835
(defn step-1 []
    (->> (prep)
        (map :sector-id)
        (reduce +)))

;; 993
(defn step-2 []
    (->> (prep)
        (map room->name)
        (filter (comp (partial = "northpole object storage") :name))
        (first)
        (:sector-id)))
