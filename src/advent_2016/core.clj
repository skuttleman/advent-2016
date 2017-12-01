(ns advent-2016.core
  (:require [advent-2016.day-1 :as day-1]))

(def ^:private solutions
  {:day-1 {:step-1 day-1/step-1 :step-2 day-1/step-2}})

(defn -main [day step & _]
  (when-let [f (get-in solutions [(keyword day) (keyword step)])]
    (println (f))))
