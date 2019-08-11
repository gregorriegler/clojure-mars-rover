(ns cljtry.core-test
  (:require [midje.sweet :refer :all]
            [cljtry.core :refer :all]))

(defn to-the-right [direction]
  (cond
    (= direction "N") "E"
    (= direction "E") "S"
    (= direction "S") "W"
    (= direction "W") "N"))

(defn to-the-left [direction]
  (cond
    (= direction "N") "W"
    (= direction "E") "N"
    (= direction "S") "E"
    (= direction "W") "S"))

(defn vector-towards [direction]
  (cond
    (= direction "N") [0, 1]
    (= direction "E") [1, 0]
    (= direction "S") [0, -1]
    (= direction "W") [-1, 0]))

(defn direction-of [rover-state]
  (last rover-state))

(defn coordinates-of [rover-state]
  (subvec rover-state 0 2))

(defn rotate [rotate, rover-state]
  (let [new-direction (rotate (direction-of rover-state))]
    (conj (coordinates-of rover-state) new-direction)))

(defn move [rover-state]
  (let [new-coordinates (vec (map + rover-state (vector-towards (direction-of rover-state))))]
    (conj new-coordinates (direction-of rover-state))))

(defn exec [rover-state, command-string]
  (def command (subs command-string 0 1))
  (def next-commands (subs command-string 1))
  (let [next-state (cond
                (= command "M") (move rover-state)
                (= command "R") (rotate to-the-right rover-state)
                (= command "L") (rotate to-the-left rover-state))]
    ((land-rover next-state) next-commands)))

(defn land-rover [rover-state]
  (fn [command-string]
    "executes commands"
    (if
      (> (count command-string) 0)
      (exec rover-state command-string)
      rover-state)))

(def rover (land-rover [0, 0, "N"]))


(fact "rover rotates to the right"
      (rover "R")    => [0, 0, "E"]
      (rover "RR")   => [0, 0, "S"]
      (rover "RRR")  => [0, 0, "W"]
      (rover "RRRR") => [0, 0, "N"])

(fact "rover rotates to the left"
      (rover "L")    => [0, 0, "W"]
      (rover "LL")   => [0, 0, "S"]
      (rover "LLL")  => [0, 0, "E"]
      (rover "LLLL") => [0, 0, "N"])

(fact "rover moves north"
      (rover "M")   => [0, 1, "N"]
      (rover "MM")  => [0, 2, "N"]
      (rover "MMM") => [0, 3, "N"])

(fact "rover moves south"
      (rover "RRM")   => [0, -1, "S"]
      (rover "RRMM")  => [0, -2, "S"]
      (rover "RRMMM") => [0, -3, "S"])

(fact "rover moves east"
      (rover "RM")   => [1, 0, "E"]
      (rover "RMM")  => [2, 0, "E"]
      (rover "RMMM") => [3, 0, "E"])

(fact "rover moves west"
      (rover "LM")   => [-1, 0, "W"]
      (rover "LMM")  => [-2, 0, "W"]
      (rover "LMMM") => [-3, 0, "W"])

