package main.model.vehicle

import main.model.vehicle.Vehicle

class Van(maxLoad: Double = 100.0) : Vehicle("Van", maxLoad) {
    private val capacity = maxLoad
    override fun maxLoad(): Double = capacity
}