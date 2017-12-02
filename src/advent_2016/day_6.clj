(ns advent-2016.day-6
    (:require [advent-2016.utils.core :as u]
              [advent-2016.utils.matrix :as matrix]))

(defn ^:private prep []
    (->> (u/read-file 6 #"\n")
        (map seq)))

(defn ^:private most-freq-letter [letters]
    (->> letters
        (group-by identity)
        (map (fn [[letter group]] [letter (count group)]))
        (sort-by (comp (partial * -1) second))
        (map first)
        (first)))

;; qtbjqiuq
(defn step-1 []
    (->> (prep)
        (matrix/rotate)
        (map most-freq-letter)
        (apply str)))
