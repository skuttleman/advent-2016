(ns advent-2016.day-3
    (:require [advent-2016.utils.core :as u]
              [advent-2016.utils.matrix :as matrix]))

(defn ^:private valid-triangle? [[a b c]]
    (and (> (+ a b) c)
        (> (+ a c) b)
        (> (+ b c) a)))

(defn ^:private process [f]
    (->> (u/read-file 3 #"\n")
        (map (partial u/split-n-trim #"\W"))
        (map (partial map #(Integer/parseInt %)))
        (f)
        (filter valid-triangle?)
        (count)))

;; 1050
(defn step-1 []
    (process identity))

;; 1921
(defn step-2 []
    (process (fn [data]
                 (->> data
                     (partition 3)
                     (mapcat matrix/rotate)))))
