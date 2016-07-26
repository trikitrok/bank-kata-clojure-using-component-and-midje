(ns bank-account.statement-format
  (:require
    [clj-time.format :as f]
    [com.stuartsierra.component :as component]
    [bank-account.amounts-formatting :refer [format-amount]]))

(defprotocol StatementFormat
  (order-lines [this statement-lines])
  (header [this])
  (format-date [this date])
  (line-format [this amount])
  (format-statement-line [this statement-line]))

(defrecord NiceReverseStatementFormat [config]
  component/Lifecycle
  (start [this]
    (println ";; Starting NiceStatementLineFormatter")
    (assoc this :date-formatter
           (f/formatter (:date-format config))))

  (stop [this]
    (println ";; Stopping NiceStatementLineFormatter")
    this)

  StatementFormat
  (format-date [{:keys [date-formatter]} date]
    (f/unparse date-formatter date))

  (line-format [_ amount]
    (let [separator (:separator config)]
      (if (neg? amount)
        (str "%s " separator " " separator " %s " separator " %s")
        (str "%s " separator " %s " separator " " separator " %s"))))

  (format-statement-line [this {:keys [amount balance date]}]
    (let [num-decimals (:num-decimals config)]
      (format (line-format this amount)
            (format-date this date)
            (format-amount (Math/abs amount) num-decimals)
            (format-amount balance num-decimals))))

  (header [_]
    (-> config :header))

  (order-lines [_ statement-lines]
    (reverse statement-lines)))

(defn nice-reverse-format [config]
  (->NiceReverseStatementFormat config))
