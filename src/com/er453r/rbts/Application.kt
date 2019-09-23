package com.er453r.rbts

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main(args: Array<String>) {
    log.info { "Start" }

    Robots(args.first(), args[1])
}
