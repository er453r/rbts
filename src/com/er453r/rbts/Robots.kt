package com.er453r.rbts

import Anki.Vector.external_interface.Behavior
import Anki.Vector.external_interface.ExternalInterfaceGrpc
import Anki.Vector.external_interface.Messages
import io.grpc.CallCredentials
import io.grpc.Metadata
import io.grpc.netty.GrpcSslContexts
import io.grpc.netty.NettyChannelBuilder
import mu.KotlinLogging
import java.io.File
import java.util.concurrent.Executor

class Robots(cert: String, token: String) {
    private val log = KotlinLogging.logger {}

    init {
        log.info { "Starting $cert" }

        val channel = NettyChannelBuilder.forAddress("192.168.0.111", 443)
            .overrideAuthority("Vector-X4X4")
            .sslContext(
                GrpcSslContexts
                    .forClient()
                    .trustManager(File(cert)) // public key
                    .build()
            )
            .build()

        val callCredentials = object : CallCredentials() {
            override fun applyRequestMetadata(p0: RequestInfo?, p1: Executor?, p2: MetadataApplier?) {
                val metadata = Metadata()

                metadata.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer $token")

                p2?.apply(metadata)
            }

            override fun thisUsesUnstableApi() {}
        }

        val vectorInterface = ExternalInterfaceGrpc.newBlockingStub(channel).withCallCredentials(callCredentials)

//        vectorInterface.driveOffCharger(Messages.DriveOffChargerRequest.newBuilder().build())

        val resp = vectorInterface.assumeBehaviorControl(
            Behavior.BehaviorControlRequest.newBuilder()
                .setControlRequest(
                    Behavior.ControlRequest.newBuilder()
                        .setPriorityValue(Behavior.ControlRequest.Priority.OVERRIDE_BEHAVIORS_VALUE)
                        .build()
                )
                .build()
        )

        log.info { "Response $resp" }

        Thread.sleep(5000)

        val response = vectorInterface.setEyeColor(
            Messages.SetEyeColorRequest.newBuilder()
                .setHue(0.83f)
                .setSaturation(0.76f)
                .build()
        )

        log.info { "Response $response" }

        val response2 = vectorInterface.batteryState(
            Messages.BatteryStateRequest.newBuilder()
                .build()
        )

        log.info { "Response $response2" }

        Thread.sleep(5000)

        channel.shutdown()
    }
}
