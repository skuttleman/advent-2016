(ns advent-2016.utils.matrix)


(defn rotate [matrix]
    (let [grid-size (count matrix)]
        (assert (every? (comp (partial = grid-size) count) matrix))
        (->> matrix
            (reduce (fn [result row]
                        (->> row
                            (map-indexed (fn [col result']
                                             (concat [result'] (get (vec result) col []))))))
                []))))
