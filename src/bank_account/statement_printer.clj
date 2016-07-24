(ns bank-account.statement-printer
  (:require
    [bank-account.statement-line-formatting :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this statement-lines]))

(defn- print-header []
  (println "date || credit || debit || balance"))

(defrecord ConsoleStatementPrinter []
  StatementPrinter
  (print-statement [_ statement-lines]
    (print-header)
    (doseq [line statement-lines]
      (println (formatting/format-statement-line line)))))

(defn use-console-printer []
  (println ";; creating ConsoleStatementPrinter")
  (->ConsoleStatementPrinter))
