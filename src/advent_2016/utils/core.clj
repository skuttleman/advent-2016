(ns advent-2016.utils.core
    (:require [clojure.string :as s]))

(defn read-file [day re]
    (->> (s/split (slurp (str "resources/day" day ".txt")) re)
        (map s/trim)
        (remove empty?)))
