(ns bank-account.account
  (:require
    [bank-account.transactions :refer [register! statement-lines]]
    [bank-account.statement-printer :as statement-printer]
    [com.stuartsierra.component :as component]))

(defprotocol AccountOperations
  (deposit! [this amount])
  (withdraw! [this amount])
  (print-statement [this]))

(defrecord Account [transactions printer]
  AccountOperations
  (deposit! [_ amount]
    (register! transactions amount))

  (withdraw! [_ amount]
    (register! transactions (- amount)))

  (print-statement [_]
    (->> (statement-lines transactions)
         (statement-printer/print-statement printer))))
