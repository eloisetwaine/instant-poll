(ns instant-poll.state
  (:require [clojure.edn :as edn]
            [mount.core :refer [defstate]]
            [discljord.messaging :as discord])
  (:import (java.util.concurrent Executors ScheduledExecutorService)))


(defstate ^ScheduledExecutorService scheduler
  :start (Executors/newSingleThreadScheduledExecutor)
  :stop (.shutdown scheduler))

(defstate polls
  :start (atom {}))

(defstate config
  :start (edn/read-string (slurp "config.edn")))

(defstate discord-conn
  :start (discord/start-connection! (:token config))
  :stop (discord/stop-connection! discord-conn))