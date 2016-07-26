(ns bank-account.amounts-formatting-test
  (:require
    [midje.sweet :refer :all]
    [bank-account.amounts-formatting :refer :all]))

(fact
  "amounts are padded with zeros up to a given number of decimals"
  (format-amount 4 1) => "4.0"
  (format-amount 4.0 3) => "4.000"
  (format-amount 4.60 4) => "4.6000"
  (format-amount 4.05 2) => "4.05")
