(ns advent-2016.day-6
    (:require [advent-2016.utils.core :as u]
              [advent-2016.utils.matrix :as matrix]))

(defn ^:private prep []
    (->> (u/read-file 6 #"\n")
        (map seq)))

(defn ^:private find-letter [sorter letters]
    (->> letters
        (group-by identity)
        (map (fn [[letter group]] [letter (count group)]))
        (sort-by sorter)
        (map first)
        (first)))

(defn ^:private solve [sorter]
    (->> (prep)
        (matrix/rotate)
        (map (partial find-letter sorter))
        (apply str)))

;; qtbjqiuq
(defn step-1 []
    (solve (comp (partial * -1) second)))

;; akothqli
(defn step-2 []
    (solve second))
