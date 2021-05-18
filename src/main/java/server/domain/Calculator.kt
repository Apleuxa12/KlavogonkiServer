package server.domain

import models.Color
import models.ColoredText
import models.Results
import models.Text
import server.UserThread
import java.util.logging.Logger

class Calculator {

    companion object {
        private val logger = Logger.getLogger(Calculator::class.java.name)

        fun calculate(coloredText: ColoredText, input: String, shift: Int): ColoredText {
            val inputColors = coloredText.colors
            val text = coloredText.text
            val stringText = text?.value!!
            val colors = coloredText.colors

            for (i in input.indices) {
                if (i + shift < stringText.length) {
                    val letter = stringText[i + shift]
                    colors[i + shift] =
                        when (letter) {
                            ' ' -> Color.SPACE
                            input[i] -> Color.RIGHT
                            else -> Color.WRONG
                        }
                }
            }

//            for(i in input.length until stringText.length){
//                if(i < stringText.length) {
//                    colors.add(
//                        when (stringText[i]) {
//                            ' ' -> Color.SPACE
//                            else -> Color.NEUTRAL
//                        }
//                    )
//                }
//            }

            return ColoredText(text, colors, shouldClear(shift, input, stringText))
        }

        private fun shouldClear(shift: Int, input: String, stringText: String): Boolean {
            if (shift + input.length > stringText.length + 1 || input.isEmpty() || stringText.isEmpty())
                return false

            return input.last() == ' ' && "$stringText ".substring(shift until (shift + input.length)) == input
        }

        fun calculateResults(text: Text, elapsed: Long) =
            Results(time = elapsed, speed = 1000 * text.value.length.toDouble() / elapsed)
    }

}