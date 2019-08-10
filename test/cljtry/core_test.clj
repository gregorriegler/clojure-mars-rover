(ns cljtry.core-test
  (:require [midje.sweet :refer :all]
            [cljtry.core :refer :all]))

(defn move [pos]
  pos + [0, 1])

(defn create-rover [pos]
  (fn [cmd] (move pos)))

(def rover (create-rover [0, 0]))

(fact "rover moves"
      (rover "M") => [0, 1]
      (rover "MM") => [0, 2])
