(ns bank-account.statement-printer
  (:require
    [bank-account.statement-line-formatting :as formatting]))

(defprotocol StatementPrinter
  (print-statement [this statement-lines]))

(defn- print-header [header]
  (println header))

(defn- print-lines [lines]
  (doall (map println lines)))

(defrecord ConsoleStatementPrinter [config formatter]
  StatementPrinter
  (print-statement [_ statement-lines]
    (print-header (-> config :header))
    (->> (reverse statement-lines)
         (map (partial formatting/format-statement-line formatter))
         print-lines)))

(defn use-console-printer [config]
  (println ";; creating ConsoleStatementPrinter")
  (map->ConsoleStatementPrinter {:config config}))
