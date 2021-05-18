package server

import data.TextDB
import models.Text
import models.User
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class Server(
    private val port: Int
) : Thread() {
    private val users = ArrayList<UserThread>()

    val text: Text by lazy { TextDB.getRandomText() }

    override fun run() {
        ServerSocket(port, 0, InetAddress.getByName(null)).use {
            println("Server started on address ${it.localSocketAddress}")
            do {
                val socket = it.accept()
                start(socket)
            }while(users.size < 4)
        }
    }

    private fun start(socket: Socket) {
        val thread = UserThread(socket, this)
        users.add(thread)
        thread.start()
    }
}