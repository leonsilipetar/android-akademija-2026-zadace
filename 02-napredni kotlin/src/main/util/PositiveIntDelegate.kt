package main.util

import kotlin.reflect.KProperty

class PositiveIntDelegate {
    private var value = 0

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: Int) {
        if (newValue >= 0) value = newValue
    }
}