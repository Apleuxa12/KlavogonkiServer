package server

import models.*
import utils.SerializationUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.logging.Logger
import kotlin.system.measureTimeMillis

class UserThread(
    private val socket: Socket,
    private val server: Server
) : Thread() {

    private val writer: PrintWriter = PrintWriter(socket.getOutputStream(), true)
    private val reader: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))

    companion object {
        private val logger = Logger.getLogger(UserThread::class.java.name)
    }

    lateinit var user: User

    lateinit var text: Text

    override fun run() {
//        Read userName
        try {
            println("User ${socket.inetAddress} connected")
            val name = readMessage<String>()
            user = User(name, User.getId())
//        Send Text
            text = server.text
            sendMessage(text)

            var message: String
            var coloredText = ColoredText(text, ArrayList(), false)
            var shift = 0

            val elapsed = measureTimeMillis {
                do {
//              Read-send messages from/to server
                    message = readMessage()
                    if (message.isEmpty())
                        continue
                    coloredText = calculate(coloredText, message, shift)
                    if (coloredText.shouldClear)
                        shift += message.length

                    sendMessage(coloredText)
                    server.showMessage(user, message)
                } while (!coloredText.isFinished)
            }
            sendMessage(calculateResults(text, elapsed))
        } catch (e: IOException) {
            logger.severe(e.message)
        } finally {
            close()
        }
    }

    private fun close() {
        reader.close()
        writer.close()
        socket.close()
    }

    private fun calculate(coloredText: ColoredText, input: String, shift: Int): ColoredText {
        val colors = ArrayList<Color>()
        val inputColors = coloredText.colors
        val text = coloredText.text
        val stringText = text?.value

        if (shift > 0 && inputColors.isNotEmpty()) {
            for (i in 0..shift)
                colors[i] = inputColors[i]
        }

        stringText?.forEachIndexed { i, letter ->
            if (i < input.length) {
                colors[i + shift] = when (letter) {
                    ' ' -> Color.SPACE
                    input[i] -> Color.RIGHT
                    else -> Color.WRONG
                }
            } else
                colors[i + shift] = Color.NEUTRAL
        }

        val shouldClear = if (shift + input.length <= stringText?.length ?: -1)
            input.last() == ' ' && stringText?.substring(shift until (shift + input.length)) == input
        else
            false

        return ColoredText(text, colors, shouldClear)
    }

    private fun calculateResults(text: Text, elapsed: Long) =
        Results(time = elapsed, speed = text.value.length.toDouble() / elapsed)

    private inline fun <reified T> readMessage(): T {
        val msg = SerializationUtils.deserializeMessage<T>(reader.readLine())
        logger.info("READ : $msg")
        return msg
    }

    private fun <T> sendMessage(message: T) {
        val msg = SerializationUtils.serializeMessage(message)
        logger.info("WRITE : $msg")
        writer.println(msg)
    }
}