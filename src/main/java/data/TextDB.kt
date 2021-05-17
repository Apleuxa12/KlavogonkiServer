package data

import models.Text
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.collections.HashMap

//DAO + Service + In-code DataBase (should implement normal DB with JPA)
object TextDB {

    private val texts = HashMap<Long, Text>()

    private var GLOBAL_ID = AtomicLong(0)

    init{
//      ADD TEXTS
        add(Text(theme = "MockTheme", value = "Mock mock mock mock mock mock mock"))
    }

    private fun add(text: Text){
        texts[GLOBAL_ID.incrementAndGet()] = text
    }

    fun getRandomText(): Text{
        return texts.entries.elementAt(Random().nextInt(texts.size)).value
    }

    fun getTextById(id: Long): Text? {
        return texts[id]
    }

    fun getTextsByTheme(theme: String): List<Text>{
        return texts.values.filter { v -> v.theme == theme }
    }
}