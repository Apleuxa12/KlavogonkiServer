package models

import utils.SerializationUtils

open class Message<T>(val value: T) {

    override fun toString(): String {
        return SerializationUtils.serializeMessage(this)
    }
}