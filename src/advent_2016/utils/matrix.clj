(ns advent-2016.utils.matrix)

(defn rotate [matrix]
    (->> matrix
        (reduce (fn [result row]
                    (->> row
                        (map-indexed (fn [col result']
                                         (concat [result'] (get (vec result) col []))))))
            [])))
