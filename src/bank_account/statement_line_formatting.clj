(ns bank-account.statement-line-formatting
  (:require
    [clj-time.format :as f]
    [com.stuartsierra.component :as component]))

(defprotocol StatementLineFormatter
  (format-date [this date])
  (pad-num [this num])
  (format-amount [this amount])
  (format-balance [this balance]))

(defrecord NiceStatementLineFormatter [config]
  component/Lifecycle
  (start [this]
    (println ";; Starting NiceStatementLineFormatter")
    (assoc this :date-formatter
           (f/formatter (:date-format config))))

  (stop [this]
    (println ";; Stopping NiceStatementLineFormatter")
    this)

  StatementLineFormatter
  (format-date [this date]
    (f/unparse (:date-formatter this) date))

  (pad-num [_ num]
    (str num ".00"))

  (format-balance [_ balance]
    (pad-num _ balance))

  (format-amount [_ amount]
    (if (< amount 0)
      (str " || || " (pad-num _ amount) " || ")
      (str " || " (pad-num _ amount) " || || "))))

(defn use-nice-statement-line-formatter [config]
  (->NiceStatementLineFormatter config))

(defn format-statement-line [formatter {:keys [amount balance date]}]
  (str (format-date formatter date)
       (format-amount formatter amount)
       (format-balance formatter balance)))