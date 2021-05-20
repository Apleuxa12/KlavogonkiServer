package main

import server.Server

fun main(args: Array<String>){
    var config = "localhost" to 8082
    if(args.size > 1)
        config = args[0] to args[1].toInt()
    Server(host = config.first, port = config.second).start()
}