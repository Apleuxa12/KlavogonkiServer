package server

import models.*
import server.domain.Calculator
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
        logger.info("Address ${socket.inetAddress} connected")
        try {
            val name = readMessage<String>(reader.readLine())
            user = User(name, User.getId())
            logger.info("${user.userName} connected")
//        Send Text
            text = server.text
            sendMessage(text)

            var message = ""
            var coloredText = ColoredText(text, createColorsInitial(text), false)
            var shift = 0
            var previous = ""

            val elapsed = measureTimeMillis {
                do {
//              Read-send messages from/to server
                    val line = reader.readLine() ?: throw NullPointerException("User ${user.userName} disconnected")
                    if (line.isEmpty())
                        continue
                    message = readMessage(line)
                    if(line != previous) {
                        coloredText = Calculator.calculate(coloredText, message, shift)
                        if (coloredText.shouldClear)
                            shift += message.length
                    }
                    previous = line

                    sendMessage(coloredText)
//                    logger.info(message)
                } while (!coloredText.isFinished)
            }
            val results = Calculator.calculateResults(text, elapsed)
            logger.info(results.toString())
            sendMessage(results)
        } catch (e: IOException) {
            logger.severe(e.message)
        } catch (e: NullPointerException) {
            logger.info(e.message)
        } finally {
            close()
        }
    }

    private fun close() {
        reader.close()
        writer.close()
        socket.close()
    }

    private fun createColorsInitial(text: Text): ArrayList<Color>{
        val colors = ArrayList<Color>()
        for(letter in text.value)
            colors.add(
                when(letter){
                    ' ' -> Color.SPACE
                    else -> Color.NEUTRAL
                }
            )
        return colors
    }

    private inline fun <reified T> readMessage(input: String): T = SerializationUtils.deserializeMessage(input)


    private fun <T> sendMessage(message: T) {
        writer.println(SerializationUtils.serializeMessage(message))
    }
}