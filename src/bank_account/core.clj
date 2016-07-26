(ns bank-account.core
  (:require
    [bank-account.account :as account]))

(defn withdraw! [account amount]
  (account/withdraw! account amount))

(defn deposit! [account amount]
  (account/deposit! account amount))

(defn print-statement [account]
  (account/print-statement account))
