(ns bank-account.statement-format-types.nice-reverse-statement-format
  (:require
    [clj-time.format :as f]
    [com.stuartsierra.component :as component]
    [bank-account.statement-format :as statement-format]
    [bank-account.amounts-formatting :refer [format-amount]]))

(defn- format-date [date-formatter date]
  (f/unparse date-formatter date))

(defn- nice-reverse-line-format [separator amount]
  (if (neg? amount)
    (str "%s " separator " " separator " %s " separator " %s")
    (str "%s " separator " %s " separator " " separator " %s")))

(defn- nice-reverse-format-statement-line [date-formatter config {:keys [amount balance date]}]
  (let [num-decimals (:num-decimals config)]
    (format (nice-reverse-line-format (:separator config) amount)
            (format-date date-formatter date)
            (format-amount (Math/abs amount) num-decimals)
            (format-amount balance num-decimals))))

(defrecord NiceReverseStatementFormat [config]
  component/Lifecycle
  (start [this]
    (println ";; Starting NiceReverseStatementFormat")
    (assoc this :date-formatter
           (f/formatter (:date-format config))))

  (stop [this]
    (println ";; Stopping NiceReverseStatementFormat")
    (assoc this :date-formatter nil))

  statement-format/StatementFormat
  (header [_]
    (-> config :header))

  (format-statement-lines [{:keys [date-formatter]} statement-lines]
    (let [format-line (partial nice-reverse-format-statement-line date-formatter config)]
      (->> (reverse statement-lines)
           (map format-line)))))

(defn make [config]
  (->NiceReverseStatementFormat config))
