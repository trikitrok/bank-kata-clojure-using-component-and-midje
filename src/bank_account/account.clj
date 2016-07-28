(ns bank-account.account
  (:require
    [bank-account.statement-printing.statement-printer :as statement-printer]
    [bank-account.transactions-operations.transactions-operations :as transactions-operations]))

(defrecord Account [transactions printer])

(defn withdraw! [{:keys [transactions]} amount]
  (transactions-operations/register! transactions (- amount)))

(defn deposit! [{:keys [transactions]} amount]
  (transactions-operations/register! transactions amount))

(defn print-statement [{:keys [printer transactions]}]
  (statement-printer/print-statement
    printer
    (transactions-operations/balanced-transactions transactions)))

(defn make []
  (map->Account {}))