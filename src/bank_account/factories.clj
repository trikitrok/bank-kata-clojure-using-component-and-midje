(ns bank-account.factories
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.transactions-operations.calendar :as calendar]
    [bank-account.account :as account]
    [bank-account.statement-printing.console-statement-printer :as console-statement-printer]
    [bank-account.transactions-operations.in-memory-transactions :as in-memory-transactions]
    [bank-account.statement-formatting.nice-reverse-statement-format :as nice-reverse-statement-format]))

(defn account []
  (component/using
    (account/make)
    {:transactions :transactions
     :printer :printer}))

(defn in-memory-transactions [current-date-fn]
  (in-memory-transactions/make current-date-fn))

(defn console-printer []
  (component/using
    (console-statement-printer/make)
    {:format :format}))

(defn nice-reverse-statement-format [config]
  (nice-reverse-statement-format/make config))

(defn make-system [conf]
  (component/system-map
    :transactions (in-memory-transactions calendar/current-date)
    :format (nice-reverse-statement-format (:format conf))
    :printer (console-printer)
    :account (account)))
