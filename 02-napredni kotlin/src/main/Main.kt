package main

import main.model.vehicle.Bike
import main.model.Courier
import main.service.DeliveryCenter
import main.model.Package
import main.model.vehicle.Van
import kotlin.random.Random
fun main() {
    val center = DeliveryCenter("Osijek Hub")

    val courier1 = Courier(1, "Ivan", 1000.0)
    courier1.assignVehicle(Bike(20.0))

    val courier2 = Courier(2, "Ana", 1000.0)
    courier2.assignVehicle(Van(80.0))

    val courier3 = Courier(3, "Marko", 1000.0)
    courier3.assignVehicle(Bike(15.0))

    center.addCourier(courier1)
    center.addCourier(courier2)
    center.addCourier(courier3)

    val totalPackages = Random.nextInt(10, 20)
    for (i in 1..totalPackages) {
        val weight = Random.nextDouble(1.0, 30.0)
        val pkg = Package("PKG$i", weight)
        center.addPackage(pkg)
    }

    val heavyPackage = Package("Heavy", 100.0)

    center.addPackage(heavyPackage)

    println("Starting shift with ${center.getNumberOfPackages()} packages...\n")

    courier2.startShift(center)

    println("\n--- STATISTICS ---")
    println("Total packages: ${center.getNumberOfPackages()}")
    println("Delivered: ${center.getDeliveredPackages().size}")
    println("Rejected: ${center.getRejectedPackages().size}")

    val totalDeliveredWeight = center.getDeliveredPackages().sumOf { it.weight }
    println("Total delivered weight: ${String.format("%.2f", totalDeliveredWeight)} kg")
}