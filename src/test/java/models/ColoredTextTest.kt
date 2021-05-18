package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ColoredTextTest{

    @Test
    fun testIsFinished(){
        assertTrue(ColoredText(null, arrayListOf(Color.RIGHT), false).isFinished)
        assertTrue(ColoredText(null, arrayListOf(Color.SPACE), false).isFinished)
        assertTrue(ColoredText(null, arrayListOf(Color.RIGHT, Color.SPACE), false).isFinished)
    }

    @Test
    fun testIsNotFinished(){
        assertFalse(ColoredText(null, arrayListOf(Color.WRONG), false).isFinished)
        assertFalse(ColoredText(null, arrayListOf(Color.NEUTRAL), false).isFinished)
        assertFalse(ColoredText(null, arrayListOf(Color.WRONG, Color.NEUTRAL), false).isFinished)
        assertFalse(ColoredText(null, arrayListOf(Color.WRONG, Color.NEUTRAL, Color.SPACE), false).isFinished)
    }

}