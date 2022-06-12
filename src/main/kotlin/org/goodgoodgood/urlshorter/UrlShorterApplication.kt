package org.goodgoodgood.urlshorter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrlShorterApplication

fun main(args: Array<String>) {
    runApplication<UrlShorterApplication>(*args)
}
