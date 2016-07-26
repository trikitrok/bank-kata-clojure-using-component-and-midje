(ns bank-account.statement-line-formatting
  (:require
    [clj-time.format :as f]
    [com.stuartsierra.component :as component]))

(defprotocol StatementLineFormatter
  (format-date [this date])
  (pad-num [this num])
  (line-format [this amount])
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
  (format-date [{:keys [date-formatter]} date]
    (f/unparse date-formatter date))

  (pad-num [_ num]
    (str num ".00"))

  (line-format [_ amount]
    (let [separator (:separator config)]
      (if (neg? amount)
        (str "%s " separator " " separator " %s " separator " %s")
        (str "%s " separator " %s " separator " " separator " %s"))))

  (format-amount [this amount]
    (pad-num this (Math/abs amount)))

  (format-balance [this balance]
    (pad-num this balance)))

(defn nice-formatter [config]
  (->NiceStatementLineFormatter config))

(defn format-statement-line [formatter {:keys [amount balance date]}]
  (format (line-format formatter amount)
          (format-date formatter date)
          (format-amount formatter amount)
          (format-balance formatter balance)))
