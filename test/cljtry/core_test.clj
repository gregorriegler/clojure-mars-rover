(ns cljtry.core-test
  (:require [midje.sweet :refer :all]
            [cljtry.core :refer :all]))

(defn move [position, other-commands]
  (let [new-position (conj (vec (map + position [0, 1])) (last position))]
    ((create-rover new-position) other-commands)))

(defn exec [position, command, other-commands]
  (= command "M") (move position other-commands))

(defn land-rover [position]
  (fn [command-string]
    (if
      (> (count command-string) 0)
      (exec position (subs command-string 0 1) (subs command-string 1))
      position)))

(def rover (land-rover [0, 0, "N"]))

(fact "rover moves"
      (rover "M")   => [0, 1, "N"]
      (rover "MM")  => [0, 2, "N"]
      (rover "MMM") => [0, 3, "N"])
