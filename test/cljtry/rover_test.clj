(ns cljtry.rover-test
  (:require [midje.sweet :refer :all]
            [cljtry.rover :refer :all]))

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

