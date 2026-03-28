package main.model.vehicle

abstract class Vehicle (
    val name: String,
    protected val maxLoad: Double
) {
    abstract fun maxLoad(): Double
    fun canCarry(weight: Double): Boolean = weight <= maxLoad
}