(ns advent-2016.day-2
    (:require [advent-2016.utils.core :as u]))

(def ^:private keypad [[1 2 3]
             [4 5 6]
             [7 8 9]])

(defn ^:private move [[y x] char]
    (case char
        \U [(dec y) x]
        \D [(inc y) x]
        \L [y (dec x)]
        \R [y (inc x)]
        [y x]))

(defn ^:private get-digit [keypad start-at direction]
    (->> direction
        (reduce (fn [position char]
                    (let [position' (move position char)]
                        (if (get-in keypad position')
                            position'
                            position))) start-at)))

(defn ^:private get-digits [keypad directions]
    (->> directions
        (reduce (fn [positions direction]
                    (conj positions (get-digit keypad (last positions) direction))) [[1 1]])))

;; 92435
(defn step-1 []
    (->> (u/read-file 2 #"\n")
        (get-digits keypad)
        (rest)
        (map (partial get-in keypad))))
