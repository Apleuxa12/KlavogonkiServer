package utils

import models.Message
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class SerializationUtilsTest{

    @Test
    fun testSerialize(){
        assertEquals(SerializationUtils.serializeMessage(Message("VALUE")), "{\"value\":\"VALUE\"}")
    }

    @Test
    fun testDeserialize(){
        assertEquals(SerializationUtils.deserializeMessage<Message<String>>("{\"value\":\"VALUE\"}").value
            , "VALUE")
    }

    @Test
    fun substringTest(){
        assertDoesNotThrow{"Lorem Ipsum".substring(0 until "Lorem Ipsum".length)}
    }
}