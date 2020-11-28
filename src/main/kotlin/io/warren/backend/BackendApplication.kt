package io.warren.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    val app = SpringApplication(BackendApplication::class.java)
    app.webApplicationType = WebApplicationType.REACTIVE
    app.run(*args)
}

