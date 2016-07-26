(ns bank-account.core
  (:require
    [com.stuartsierra.component :as component]
    [bank-account.statement-printer :as printer]
    [bank-account.transactions :as transactions]
    [bank-account.calendar :as calendar]
    [bank-account.account :as account]
    [bank-account.statement-line-formatting :as statement-line-formatting]))

(defn make-system [conf]
  (component/system-map
    :transactions (transactions/in-memory calendar/current-date)
    :formatter (statement-line-formatting/nice-formatter (:formatter conf))
    :printer (printer/console-printer (:printer conf) :formatter)
    :account (account/new :transactions :printer)))

(defn withdraw! [account amount]
  (account/withdraw! account amount))

(defn deposit! [account amount]
  (account/deposit! account amount))

(defn print-statement [account]
  (account/print-statement account))
