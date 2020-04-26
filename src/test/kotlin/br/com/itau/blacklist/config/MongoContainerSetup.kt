package br.com.itau.blacklist.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy

class MongoContainerSetup : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        val mongo: GenericContainer<*> = GenericContainer<Nothing>("mongo:4.0")
                .withExposedPorts(27017)
        mongo.setPortBindings(listOf("27017:27017"))
        mongo.start()
        mongo.waitingFor(LogMessageWaitStrategy().withRegEx("Container mongo:4.0 started"))

    }
}
