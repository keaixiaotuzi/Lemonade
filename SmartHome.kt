import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
        protected set

    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }

    fun printDeviceInfo() {
        println("Device name: $name \n" +
                "Category: $category \n" +
                "Type: $deviceType")
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    fun decreaseSpeakerVolume() {
        speakerVolume--
        println("Speaker volume decreased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    fun previousChannel() {
        channelNumber--
        println("Channel number decreased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var deviceTurnOnCount = 0
        private set

    fun turnOnTv() {
        if (smartTvDevice.deviceStatus == "off") {
            deviceTurnOnCount++
            smartTvDevice.turnOn()
        } else {
            println("Smart TV is already on.")
        }
    }

    fun turnOffTv() {
        if (smartTvDevice.deviceStatus == "on") {
            deviceTurnOnCount--
            smartTvDevice.turnOff()
        } else {
            println("Smart TV is already off.")
        }
    }

    fun increaseTvVolume() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.increaseSpeakerVolume()
        } else {
            println("Smart TV is off. Cannot adjust volume.")
        }
    }

    fun decreaseTvVolume() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.decreaseSpeakerVolume()
        } else {
            println("Smart TV is off. Cannot adjust volume.")
        }
    }

    fun changeTvChannelToNext() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.nextChannel()
        } else {
            println("Smart TV is off. Cannot change channel.")
        }
    }

    fun changeTvChannelToPrevious() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.previousChannel()
        } else {
            println("Smart TV is off. Cannot change channel.")
        }
    }

    fun printSmartTvInfo() {
        smartTvDevice.printDeviceInfo()
    }

    fun turnOnLight() {
        if (smartLightDevice.deviceStatus == "off") {
            deviceTurnOnCount++
            smartLightDevice.turnOn()
        } else {
            println("Smart Light is already on.")
        }
    }

    fun turnOffLight() {
        if (smartLightDevice.deviceStatus == "on") {
            deviceTurnOnCount--
            smartLightDevice.turnOff()
        } else {
            println("Smart Light is already off.")
        }
    }

    fun increaseLightBrightness() {
        if (smartLightDevice.deviceStatus == "on") {
            smartLightDevice.increaseBrightness()
        } else {
            println("Smart Light is off. Cannot adjust brightness.")
        }
    }

    fun decreaseLightBrightness() {
        if (smartLightDevice.deviceStatus == "on") {
            smartLightDevice.decreaseBrightness()
        } else {
            println("Smart Light is off. Cannot adjust brightness.")
        }
    }

    fun printSmartLightInfo() {
        smartLightDevice.printDeviceInfo()
    }

    fun turnOffAllDevices() {
        if (smartTvDevice.deviceStatus == "on") {
            turnOffTv()
        }
        if (smartLightDevice.deviceStatus == "on") {
            turnOffLight()
        }
        println("All devices turned off. Device turn on count: $deviceTurnOnCount")
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()

    smartDevice.printDeviceInfo()

    println("\n--- Testing SmartHome ---")
    val mySmartTv = SmartTvDevice("Living Room TV", "Entertainment")
    val mySmartLight = SmartLightDevice("Bedroom Light", "Utility")
    val mySmartHome = SmartHome(mySmartTv, mySmartLight)

    println("\nInitial device on count: ${mySmartHome.deviceTurnOnCount}")

    mySmartHome.turnOnTv()
    mySmartHome.increaseTvVolume()
    mySmartHome.changeTvChannelToNext()
    mySmartHome.changeTvChannelToNext()
    mySmartHome.decreaseTvVolume()
    mySmartHome.changeTvChannelToPrevious()
    mySmartHome.printSmartTvInfo()
    println("Device on count: ${mySmartHome.deviceTurnOnCount}")

    mySmartHome.turnOnLight()
    mySmartHome.increaseLightBrightness()
    mySmartHome.increaseLightBrightness()
    mySmartHome.decreaseLightBrightness()
    mySmartHome.printSmartLightInfo()
    println("Device on count: ${mySmartHome.deviceTurnOnCount}")

    mySmartHome.turnOffTv()
    mySmartHome.turnOffLight()
    println("Device on count after turning off: ${mySmartHome.deviceTurnOnCount}")

    println("\nAttempting to increase volume when TV is off:")
    mySmartHome.increaseTvVolume()

    println("\nAttempting to turn on already on TV:")
    mySmartHome.turnOnTv()

    println("\nAttempting to turn off already off Light:")
    mySmartHome.turnOffLight()

    println("\nTurning on devices for turnOffAllDevices test:")
    mySmartHome.turnOnTv()
    mySmartHome.turnOnLight()
    println("Device on count before turning off all: ${mySmartHome.deviceTurnOnCount}")
    mySmartHome.turnOffAllDevices()
    println("Device on count after turning off all: ${mySmartHome.deviceTurnOnCount}")
}