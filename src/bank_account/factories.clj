(ns bank-account.factories
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.statement-printer :as printer]
    [bank-account.calendar :as calendar]
    [bank-account.account :as account]
    [bank-account.transactions-repository-types.in-memory-transactions :as in-memory-transactions]
    [bank-account.statement-format-types.nice-reverse-statement-format :as nice-reverse-statement-format]))

(defn account-component-map []
  (component/using
    (account/map->Account {})
    {:transactions :transactions
     :printer :printer}))

(defn in-memory-transactions [current-date-fn]
  (in-memory-transactions/make current-date-fn))

(defn console-printer []
  (component/using
    (printer/map->ConsoleStatementPrinter {})
    {:format :format}))

(defn nice-reverse-statement-format [config]
  (nice-reverse-statement-format/make config))

(defn make-system [conf]
  (component/system-map
    :transactions (in-memory-transactions calendar/current-date)
    :format (nice-reverse-statement-format (:format conf))
    :printer (console-printer)
    :account (account-component-map)))
