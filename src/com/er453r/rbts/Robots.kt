package com.er453r.rbts

import Anki.Vector.external_interface.ExternalInterfaceGrpc
import Anki.Vector.external_interface.Messages
import io.grpc.ManagedChannelBuilder
import mu.KotlinLogging

class Robots(name: String) {
    private val log = KotlinLogging.logger {}

    init {
        log.info { "Starting $name" }

        val channel = ManagedChannelBuilder.forAddress("localhost", 443)
            .usePlaintext()
            .build()

        val vectorInterface = ExternalInterfaceGrpc.newBlockingStub(channel)

        vectorInterface.setEyeColor(Messages.SetEyeColorRequest
            .newBuilder()
            .setHue(360.0f)
            .setSaturation(100.0f)
            .build())

        channel.shutdown()
    }
}
