(ns mjhika.stream.animations.interface
  (:require
   [quil.core :as q]
   [quil.middleware :as m]))

(defn half-width [] (/ (q/width) 2))
(defn half-height [] (/ (q/height) 2))
(defn horizon-height []
  (* 1.618 (/ (q/height)
              3)))

(defn horizon []
  (q/line [0 (horizon-height)]
          [(q/width) (horizon-height)]))

(defn y-axis []
  (q/line [(half-width) (horizon-height)]
          [(half-width) (q/height)]))

(defn new-star [{:keys [x y r]}]
  (q/ellipse x y r r))

(defn setup-stars [qty]
  (vec (for [_ (range qty)]
         {:x (q/random 5 (- (q/width) 5))
          :y (q/random 5 (- (horizon-height) 5))
          :r (q/random 0.5 1.5)})))

(defn update-stars [state]
  (vec (for [star (:stars state)]
         {:x (:x star)
          :y (:y star)
          :r (q/random 0.5 1.5)})))

(defn setup []
  (q/frame-rate 1)
  (q/background 0 10)
  (q/stroke 0 255 0)
  (q/no-fill)
  {:stars (setup-stars 85)})

(defn draw [state]
  (q/frame-rate 1)
  (q/background 0 10)
  (horizon)
  (y-axis)
  (mapv new-star (:stars state)))

(defn update-state [state]
  {:stars (update-stars state)})

(q/defsketch animate-me
  :title "someday"
  :features [:keep-on-top]
  :settings #(q/smooth 2)
  :size [680 420]
  :setup #'setup
  :update #'update-state
  :draw #'draw
  :middleware [m/fun-mode])

(comment)
