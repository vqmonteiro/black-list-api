package br.com.itau.blacklist.migration

import br.com.itau.blacklist.migration.changesets.ClientChangeLog
import com.github.cloudyrock.mongock.Mongock
import com.github.cloudyrock.mongock.SpringMongockBuilder
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MigrationMongo {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var MONGO_URI: String

    private val SPRING_BOOT_MONGO_DB = "cliente_black_list"

    @Bean("mongock-spring-boot")
    fun mongockSpringBoot(): Mongock {
        val mongoclient = MongoClient(MongoClientURI(MONGO_URI))
        return SpringMongockBuilder(mongoclient, SPRING_BOOT_MONGO_DB, ClientChangeLog::class.java.getPackage().name)
                .setLockQuickConfig()
                .build()
    }

}
