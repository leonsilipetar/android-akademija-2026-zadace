class Controller(
    val smartDevice: Device
) {
    fun operate() {
        smartDevice.turnOn()
        smartDevice.turnOff()
        if(smartDevice is Dimmable){
            smartDevice.dim()
        }
    }
}

interface Dimmable {
    fun dim()
}
abstract class Device {
    abstract val name: String
    abstract val isOn: Boolean

    abstract fun turnOn()
    abstract fun turnOff()
}

class SmartFridge(
    override val name: String,
) : Device() {
    override val isOn: Boolean
        get() = TODO("Not yet implemented")

    override fun turnOn() {
        TODO("Not yet implemented")
    }

    override fun turnOff() {
        TODO("Not yet implemented")
    }
}

class SmartBulb(override val name: String, override val isOn: Boolean) : Device(), Dimmable {
    override fun turnOn() {
        TODO("Not yet implemented")
    }

    override fun turnOff() {
        TODO("Not yet implemented")
    }

    override fun dim() {
        TODO("Not yet implemented")
    }

}
