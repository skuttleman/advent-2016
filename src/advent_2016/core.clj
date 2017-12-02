(ns advent-2016.core
  (:require [advent-2016.day-1 :as day-1]
            [advent-2016.day-2 :as day-2]
            [advent-2016.day-3 :as day-3]
            [advent-2016.day-4 :as day-4]
            [advent-2016.day-5 :as day-5]
            [advent-2016.day-6 :as day-6]
            [clojure.string :as s]))

(def steps
  [[day-1/step-1 day-1/step-2]
   [day-2/step-1 day-2/step-2]
   [day-3/step-1 day-3/step-2]
   [day-4/step-1 day-4/step-2]
   [day-5/step-1 day-5/step-2]
   [day-6/step-1]])

(defn -main [& [day step]]
  (let [[day' step'] (map (fn [v] (-> (str "0" v)
                                    (s/replace #"\D" "")
                                    (Integer/parseInt)
                                    (dec))) [day step])
        step (get-in steps [day' step'])]
    (when step (println (step)))))
