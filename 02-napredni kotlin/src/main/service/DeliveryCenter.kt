package main.service

import main.model.Courier
import main.model.Package
import main.model.PackageStatus

class DeliveryCenter (
    val name : String
) {
    private val packages = mutableListOf<Package>()
    private val couriers = mutableListOf<Courier>()

    fun addPackage(pkg: Package) {
        packages.add(pkg)
    }

    fun addCourier(courier: Courier) {
        couriers.add(courier)
    }

    fun assignPackage(pkg: Package, courier: Courier): Boolean {
        val canCarry = courier.canCarry(pkg)
        val isReady = courier.isReady()

        return if (isReady && canCarry) {
            courier.assigPackage(pkg)
            pkg.markAsAssigned()
            true
        } else {
            false
        }
    }

    fun getNumberOfPackages(): Int = packages.size

    fun getDeliveredPackages(): List<Package> {
        return packages.filter { it.getStatus() == PackageStatus.DELIVERED }
    }

    fun getRejectedPackages(): List<Package> {
        return packages.filter { it.getStatus() == PackageStatus.REJECTED }
    }

    fun findCouriers(predicate: (Courier) -> Boolean): List<Courier> {
        return couriers.filter(predicate)
    }

    fun startShift() {
        println("Starting shift...\n")

        var round = 1

        while (true) {
            println("---- Round $round ----")

            val pendingPackages = packages.filter { it.getStatus() == PackageStatus.PENDING }

            println("Pending left: ${pendingPackages.size}")

            if (pendingPackages.isEmpty()) break

            var assignedInThisRound = false

            for (pkg in pendingPackages) {
                val assigned = couriers.any { courier ->
                    assignPackage(pkg, courier)
                }

                if (assigned) {
                    assignedInThisRound = true
                }
            }

            // delivery
            couriers.forEach { courier ->
                if(!courier.isReady()) {
                    courier.deliver()
                }
            }

            if (!assignedInThisRound) break

            round++
        }
        packages
            .filter { it.getStatus() == PackageStatus.PENDING }
            .forEach {
                it.markAsRejected()
                println("${it.id} could not be delivered → REJECTED")
            }
    }
}