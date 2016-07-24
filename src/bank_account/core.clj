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
    :transactions (transactions/use-in-memory
                    calendar/current-date)
    :formatter (statement-line-formatting/use-nice-statement-line-formatter (:formatter conf))
    :printer (component/using
               (printer/use-console-printer (:printer conf))
               {:formatter :formatter})
    :account (component/using
               (account/make)
               {:transactions :transactions
                :printer :printer})))

(defn withdraw! [account amount]
  (account/withdraw! account amount))

(defn deposit! [account amount]
  (account/deposit! account amount))

(defn print-statement [account]
  (account/print-statement account))

(defn start-account []
  (-> (make-system {:printer {:header "date || credit || debit || balance"}
                    :formatter {:date-format "dd/MM/yyyy"}})
      component/start
      :account))
