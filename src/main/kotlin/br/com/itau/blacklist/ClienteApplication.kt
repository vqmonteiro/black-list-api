package br.com.itau.blacklist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClienteApplication

fun main(args: Array<String>) {
	runApplication<ClienteApplication>(*args)
}
