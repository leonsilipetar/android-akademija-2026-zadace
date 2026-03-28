package main.model.vehicle

import main.model.vehicle.Vehicle

class Bike(maxLoad: Double = 5.0) : Vehicle("Bike", maxLoad) {
    private val capacity = maxLoad
    override fun maxLoad(): Double = capacity
}