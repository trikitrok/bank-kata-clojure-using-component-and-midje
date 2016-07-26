(ns bank-account.transactions-test
  (:require
    [midje.sweet :refer :all]
    [midje.open-protocols :refer [defrecord-openly]]
    [bank-account.transactions :as transactions]
    [bank-account.factories :as factories]
    [com.stuartsierra.component :as component]
    [bank-account.test-helpers :refer [make-date]]))

(unfinished date-fn)

(defn new-in-memory-transactions [date-fn]
  (component/start (factories/in-memory-transactions date-fn)))

(fact
  "about transactions"

  (facts
    "in memory"

    (fact
      "returns the statement lines for all registered transactions"

      (let [date (partial make-date "dd/MM/yyyy")
            first-transaction {:amount 1000 :date (date "10/02/2016")}
            second-transaction {:amount 1500 :date (date "13/05/2016")}
            third-transaction {:amount -500 :date (date "14/08/2016")}
            in-memory-transactions (new-in-memory-transactions #(date-fn))]

        (do
          (transactions/register! in-memory-transactions (:amount first-transaction))
          (transactions/register! in-memory-transactions (:amount second-transaction))
          (transactions/register! in-memory-transactions (:amount third-transaction))
          (transactions/statement-lines in-memory-transactions))

        => [{:amount (:amount first-transaction), :balance 1000, :date (:date first-transaction)}
            {:amount (:amount second-transaction), :balance 2500, :date (:date second-transaction)}
            {:amount (:amount third-transaction), :balance 2000, :date (:date third-transaction)}]

        (provided (date-fn) =streams=> [(:date first-transaction) (:date second-transaction) (:date third-transaction)])))))
