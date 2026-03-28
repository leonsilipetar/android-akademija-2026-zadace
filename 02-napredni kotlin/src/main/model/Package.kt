package main.model

import main.model.PackageStatus

class Package (
    val id: String,
    val weight: Double
) : Deliverable {
    private var status: PackageStatus = PackageStatus.PENDING

    override fun deliver(): Boolean {
        status = PackageStatus.DELIVERED
        return true
    }

    fun getStatus(): PackageStatus = status

    fun markAsAssigned() { status = PackageStatus.ASSIGNED }
    fun markAsDelivered() { status = PackageStatus.DELIVERED }
    fun markAsRejected() { status = PackageStatus.REJECTED }

    override fun toString(): String = "$id (${String.format("%.2f", weight)}kg)"

}