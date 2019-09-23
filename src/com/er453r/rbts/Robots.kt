package com.er453r.rbts

import Anki.Vector.external_interface.ExternalInterfaceGrpc
import Anki.Vector.external_interface.Messages
import io.grpc.netty.GrpcSslContexts
import io.grpc.netty.NettyChannelBuilder
import mu.KotlinLogging
import java.io.File

class Robots(name: String) {
    private val log = KotlinLogging.logger {}

    init {
        log.info { "Starting $name" }

        val channel = NettyChannelBuilder.forAddress("localhost", 443)
            .sslContext(
                GrpcSslContexts
                    .forClient()
                    .trustManager(File("/my-public-key-cert.pem")) // public key
                    .build()
            )
            .build()

        val vectorInterface = ExternalInterfaceGrpc.newBlockingStub(channel)

        vectorInterface.setEyeColor(
            Messages.SetEyeColorRequest
                .newBuilder()
                .setHue(360.0f)
                .setSaturation(100.0f)
                .build()
        )

        channel.shutdown()
    }
}
