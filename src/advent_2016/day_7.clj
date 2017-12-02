(ns advent-2016.day-7
    (:require [advent-2016.utils.core :as u]))

(defn ^:private has? [re s]
    (->> s
        (re-seq re)
        (filter (fn [[_ a b]] (not= a b)))
        (seq)))

(def ^:private has-abba-in-brackets?
    (partial has? #"\[[^\]]*([a-z])([a-z])\2\1.*\]"))

(def ^:private has-abbas?
    (partial has? #"([a-z])([a-z])\2\1"))

;; 105
(defn step-1 []
    (->> (u/read-file 7 #"\n")
        (remove has-abba-in-brackets?)
        (filter has-abbas?)
        (count)))
