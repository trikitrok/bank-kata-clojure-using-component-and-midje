(ns bank-account.statement-printer
  (:require
    [clj-time.format :as f]))

(defprotocol StatementPrinter
  (print-statement [this statement-lines]))

(def ^:private date-formatter
  (f/formatter "dd/MM/yyyy"))

(defn- format-date [date]
  (f/unparse date-formatter date))

(defn pad-num [num]
  (str num ".00"))

(defn- format-balance [balance]
  (pad-num balance))

(defn- format-amount [amount]
  (if (< amount 0)
    (str " || || " (pad-num amount) " || ")
    (str " || " (pad-num amount) " || || ")))

(defn- format-line [{:keys [amount balance date]}]
  (str (format-date date)
       (format-amount amount)
       (format-balance balance)))

(defn- print-header []
  (println "date || credit || debit || balance"))

(defrecord ConsoleStatementPrinter []
  StatementPrinter
  (print-statement [_ statement-lines]
    (print-header)
    (doseq [line statement-lines]
      (println (format-line line)))))

(defn use-console-printer []
  (println ";; creating ConsoleStatementPrinter")
  (->ConsoleStatementPrinter))
