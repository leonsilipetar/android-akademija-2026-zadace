package model

import main.service.DeliveryCenter

open class Employee(
    val id: Int,
    val name: String,
    val salary: Double
) {
    fun startShift(center: DeliveryCenter) {
        center.startShift()
    }

    override fun toString(): String = "$name (#$id, salary: $salary)"
}