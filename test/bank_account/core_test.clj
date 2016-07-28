(ns bank-account.core-test
  (:require
    [midje.sweet :refer :all]
    [bank-account.core :refer :all]
    [bank-account.test-helpers :refer [output-lines make-dates]]
    [com.stuartsierra.component :as component]
    [bank-account.factories :as factories]))

(unfinished date-fn)

(facts
  "printing an account statement"

  (let [config {:format {:date-format "dd/MM/yyyy"
                         :separator "||"
                         :num-decimals 2
                         :header "date || credit || debit || balance"}}
        dates (partial make-dates "dd/MM/yyyy")
        account-system (assoc (factories/make-system config)
                              :transactions
                              (factories/in-memory-transactions #(date-fn)))
        account (-> account-system component/start :account)]

    (do
      (deposit! account 1000)
      (deposit! account 2000)
      (withdraw! account 500)
      (output-lines
        print-statement account)) => ["date || credit || debit || balance"
                                      "14/01/2012 || || 500.00 || 2500.00"
                                      "13/01/2012 || 2000.00 || || 3000.00"
                                      "10/01/2012 || 1000.00 || || 1000.00"]

    (provided (date-fn) =streams=> (dates ["10/01/2012" "13/01/2012" "14/01/2012"]))))
