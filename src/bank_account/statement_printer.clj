(ns bank-account.statement-printer
  (:require
    [bank-account.statement-format :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this statement-lines]))

(defn- print [format lines]
  (->> (formatting/order-lines format lines)
       (map (partial formatting/format-statement-line format))
       (map println)
       doall))

(defrecord ConsoleStatementPrinter [format]
  StatementPrinter
  (print-statement [_ statement-lines]
    (println (formatting/header format))
    (print format statement-lines)))
