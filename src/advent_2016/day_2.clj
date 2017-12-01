(ns advent-2016.day-2
    (:require [advent-2016.utils.core :as u]))

(def ^:private keypad
    [[1 2 3]
     [4 5 6]
     [7 8 9]])

(def ^:private keypad-2
    (let [_ nil A "A" B "B" C "C" D "D"]
        [[_ _ 1 _ _]
         [_ 2 3 4 _]
         [5 6 7 8 9]
         [_ A B C _]
         [_ _ D _ _]]))

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

(defn ^:private get-digits [keypad start-at directions]
    (->> directions
        (reduce (fn [positions direction]
                    (conj positions (get-digit keypad (last positions) direction))) [start-at])))

(defn get-code [keypad start-at]
    (->> (u/read-file 2 #"\n")
        (get-digits keypad start-at)
        (rest)
        (map (partial get-in keypad))
        (apply str)))

;; 92435
(defn step-1 []
    (get-code keypad [1 1]))

;; C1A88
(defn step-2 []
    (get-code keypad-2 [2 0]))
