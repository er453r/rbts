package com.er453r.rbts

import io.grpc.ManagedChannelBuilder
import mu.KotlinLogging

class Robots(name: String) {
    private val log = KotlinLogging.logger {}

    init {
        log.info { "Starting $name" }

        val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build()



        channel.shutdown()
    }
}
