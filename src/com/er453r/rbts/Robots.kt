package com.er453r.rbts

import mu.KotlinLogging

class Robots(name: String) {
    private val log = KotlinLogging.logger {}

    init {
        log.info { "Starting $name" }
    }
}
