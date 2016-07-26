(ns bank-account.core-test
  (:require
    [midje.sweet :refer :all]
    [bank-account.core :refer :all]
    [bank-account.test-helpers :refer [output-lines make-dates]]
    [com.stuartsierra.component :as component]
    [bank-account.transactions :as transactions]))

(unfinished date-fn)

(facts
  "printing an account statement"

  (let [config {:printer {:header "date || credit || debit || balance"}
                :formatter {:date-format "dd/MM/yyyy"
                            :separator "||"
                            :num-decimals 2}}
        dates (partial make-dates "dd/MM/yyyy")
        system (assoc (make-system config)
                      :transactions
                      (transactions/in-memory #(date-fn)))
        account (-> system component/start :account)]

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
