(ns bank-account.core-test
  (:require [midje.sweet :refer :all]
            [bank-account.core :refer :all]
            [clojure.string :as string]))

(facts
  "printing an account statement"

  (let [account (start-account)]
    (deposit! account 2000)
    (deposit! account 500)
    (withdraw! account 1000)

    (string/split
      (with-out-str (print-statement account))
      #"\n") => ["date || credit || debit || balance"
                 "21/07/2016 || 2000.00 || || 2000.00"
                 "21/07/2016 || 500.00 || || 2500.00"
                 "21/07/2016 || || -1000.00 || 1500.00"]))