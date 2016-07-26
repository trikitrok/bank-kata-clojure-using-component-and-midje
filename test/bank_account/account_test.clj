(ns bank-account.account-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [bank-account.transactions :refer [TransactionsOperations]]
    [bank-account.statement-printer :refer [StatementPrinter]]
    [bank-account.account :as account]
    [bank-account.factories :as factories]
    [com.stuartsierra.component :as component]))

(unfinished register!)
(unfinished balanced-transactions)
(unfinished print-statement)

(defrecord-openly FakeTransactions []
  TransactionsOperations
  (register! [this amount])
  (balanced-transactions [this]))

(defrecord-openly FakePrinter []
  StatementPrinter
  (print-statement [this balanced-transactions]))

(defn new-account [transactions printer]
  (-> (factories/account-component-map)
      (merge {:transactions transactions
              :printer printer})
      component/start))

(facts
  "about account operations"

  (fact
    "it registers deposit transactions"
    (let [fake-transactions (->FakeTransactions)
          an-account (new-account fake-transactions :not-used)]

      (account/deposit! an-account 50) => irrelevant

      (provided
        (register! fake-transactions 50) => irrelevant :times 1)))

  (fact
    "it registers withdrawals transactions"
    (let [fake-transactions (->FakeTransactions)
          an-account (new-account fake-transactions :not-used)]

      (account/withdraw! an-account 100) => irrelevant

      (provided
        (register! fake-transactions -100) => irrelevant :times 1)))

  (fact
    "it prints the transactions in the statement"
    (let [fake-transactions (->FakeTransactions)
          fake-printer (->FakePrinter)
          an-account (new-account fake-transactions fake-printer)]

      (account/print-statement an-account) => irrelevant

      (provided
        (balanced-transactions fake-transactions) => ...some-balanced-transactions... :times 1
        (print-statement fake-printer ...some-balanced-transactions...) => irrelevant :times 1))))
