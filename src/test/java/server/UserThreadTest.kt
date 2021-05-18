package server

import models.Color
import models.ColoredText
import models.Text
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import server.domain.Calculator

internal class UserThreadTest {

    private val text = Text(theme = "theme", value = "one two three")

    @Test
    fun testCalculateRight() {
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)
    }

    @Test
    fun testCalculateRight2() {
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)

        coloredText = calculate(coloredText, "on", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.NEUTRAL)
        assertEquals(coloredText.colors[3], Color.SPACE)
    }

    @Test
    fun testCalculateRight3() {
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)

        coloredText = calculate(coloredText, "on", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.NEUTRAL)
        assertEquals(coloredText.colors[3], Color.SPACE)

        coloredText = calculate(coloredText, "onc", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.WRONG)
        assertEquals(coloredText.colors[3], Color.SPACE)
    }

    @Test
    fun testCalculateRight4(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)
        assertEquals(coloredText.colors[4], Color.NEUTRAL)
        assertTrue(coloredText.shouldClear)
    }

    @Test
    fun testCalculateGreaterThenLength(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "two ", 4)
        assertEquals(coloredText.colors[4], Color.RIGHT)
        assertEquals(coloredText.colors[5], Color.RIGHT)
        assertEquals(coloredText.colors[6], Color.RIGHT)
        assertEquals(coloredText.colors[7], Color.SPACE)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "three ", 8)
        assertEquals(coloredText.colors[8], Color.RIGHT)
        assertEquals(coloredText.colors[9], Color.RIGHT)
        assertEquals(coloredText.colors[10], Color.RIGHT)
        assertEquals(coloredText.colors[11], Color.RIGHT)
        assertEquals(coloredText.colors[12], Color.RIGHT)
        assertTrue(coloredText.shouldClear)
    }

    @Test
    fun testCalculateLongWord(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertEquals(coloredText.colors[3], Color.SPACE)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "two ", 4)
        assertEquals(coloredText.colors[4], Color.RIGHT)
        assertEquals(coloredText.colors[5], Color.RIGHT)
        assertEquals(coloredText.colors[6], Color.RIGHT)
        assertEquals(coloredText.colors[7], Color.SPACE)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "three  ", 8)
        assertEquals(coloredText.colors[8], Color.RIGHT)
        assertEquals(coloredText.colors[9], Color.RIGHT)
        assertEquals(coloredText.colors[10], Color.RIGHT)
        assertEquals(coloredText.colors[11], Color.RIGHT)
        assertEquals(coloredText.colors[12], Color.RIGHT)
        assertFalse(coloredText.shouldClear)
    }

    @Test
    fun testCalculateWrong() {
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "onc", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.WRONG)
        assertEquals(coloredText.colors[3], Color.SPACE)
    }

    @Test
    fun testCalculateWithClear(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertTrue(coloredText.shouldClear)
    }

    @Test
    fun testCalculateManyWords(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "two", 4)
        assertEquals(coloredText.colors[4], Color.RIGHT)
        assertEquals(coloredText.colors[5], Color.RIGHT)
        assertEquals(coloredText.colors[6], Color.RIGHT)
        assertFalse(coloredText.shouldClear)
    }

    @Test
    fun testCalculateManyWords2(){
        var coloredText = ColoredText(text, ArrayList(), false)

        coloredText = calculate(coloredText, "one ", 0)
        assertEquals(coloredText.colors[0], Color.RIGHT)
        assertEquals(coloredText.colors[1], Color.RIGHT)
        assertEquals(coloredText.colors[2], Color.RIGHT)
        assertTrue(coloredText.shouldClear)

        coloredText = calculate(coloredText, "twooo", 4)
        assertEquals(coloredText.colors[4], Color.RIGHT)
        assertEquals(coloredText.colors[5], Color.RIGHT)
        assertEquals(coloredText.colors[6], Color.RIGHT)
        assertEquals(coloredText.colors[8], Color.WRONG)
        assertFalse(coloredText.shouldClear)
    }

    private fun calculate(coloredText: ColoredText, input: String, shift: Int): ColoredText {
        return Calculator.calculate(coloredText, input, shift)
    }
}