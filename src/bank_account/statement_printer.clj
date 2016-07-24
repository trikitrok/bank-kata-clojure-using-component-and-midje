(ns bank-account.statement-printer
  (:require
    [bank-account.statement-line-formatting :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this statement-lines]))

(defn- print-header [header]
  (println header))

(defn- print-lines [lines]
  (doall (map println lines)))

(defn- format-and-print-lines [lines]
  (->> lines
       (map formatting/format-statement-line)
       print-lines))

(defrecord ConsoleStatementPrinter [config]
  StatementPrinter
  (print-statement [_ statement-lines]
    (print-header (-> config :header))
    (format-and-print-lines statement-lines)))

(defn use-console-printer [config]
  (println ";; creating ConsoleStatementPrinter")
  (->ConsoleStatementPrinter config))
