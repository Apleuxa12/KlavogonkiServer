package models

import java.util.concurrent.atomic.AtomicLong

data class User(val userName: String, val id: Long){

    companion object{
        var GLOBAL_ID = AtomicLong(0)

        fun getId(): Long{
            return GLOBAL_ID.incrementAndGet()
        }
    }

}