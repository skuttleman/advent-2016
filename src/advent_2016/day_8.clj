(ns advent-2016.day-8
    (:require [advent-2016.utils.core :as u]
              [advent-2016.utils.matrix :as matrix]))

(defn make-grid [width height]
    (repeat height (repeat width \.)))

(defn rect [grid width height]
    (->> grid
        (map-indexed (fn [i row] (if (>= i height)
                                     row
                                     (concat (repeat width \#) (drop width row)))))))

(defn ^:private parse-instruction [instruction]
    (->> [[rect #"rect (\d+)x(\d+)"]
          [matrix/rotate-row #"rotate row y=(\d+) by (\d+)"]
          [matrix/rotate-col #"rotate column x=(\d+) by (\d+)"]]
        (u/map-second (comp seq (partial map u/parse-int) rest #(re-matches % instruction)))
        (filter second)
        (first)))

(defn ^:private apply-instructions []
    (->> (u/read-file 8 #"\n")
        (map parse-instruction)
        (reduce (fn [grid [f args]] (apply f grid args)) (make-grid 50 6))))

(defn ^:private print-grid [grid]
    (doall (map (comp println (partial apply str)) grid))
    grid)

;;121
(defn step-1 []
    (->> (apply-instructions)
        (reduce (fn [total row] (+ total (count (filter (partial = \#) row)))) 0)))

;; RURUCEOEIL
(defn step-2 []
    (->> (apply-instructions)
        (print-grid))
    nil)
