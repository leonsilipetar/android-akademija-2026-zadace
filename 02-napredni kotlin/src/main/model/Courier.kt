package main.model

import main.util.PositiveIntDelegate
import main.model.PackageStatus
import main.model.vehicle.Vehicle
import model.Employee

class Courier(
    id: Int,
    name: String,
    salary: Double
) : Employee(id, name, salary) {
    lateinit var vehicle: Vehicle
            private set
    private var currentPackage: Package? = null
    var deliveredCount: Int by PositiveIntDelegate()

    fun deliver(): Boolean{
        val pkg = currentPackage ?: return false

        if(!isVehicleInitialised()) return false

        return if(pkg.weight <= vehicle.maxLoad()){
            pkg.markAsDelivered()
            currentPackage = null
            deliveredCount++

            println("${name} delivered ${pkg.toString()}")

            true
        } else {
            false
        }
    }

    fun assignVehicle(vehicle: Vehicle) {
        this.vehicle = vehicle
    }

    fun assigPackage(pkg: Package) {
        currentPackage = pkg
    }

    fun isVehicleInitialised(): Boolean{
        if(::vehicle.isInitialized)
            return true
        return false
    }

    fun isReady(): Boolean{
        return isVehicleInitialised() && currentPackage == null
    }

    fun canCarry(pkg: Package): Boolean {
        if (!isVehicleInitialised()) return false
        return pkg.weight <= vehicle.maxLoad()
    }
}