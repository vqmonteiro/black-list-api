package br.com.itau.blacklist.migration.changesets

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.client.MongoDatabase

@ChangeLog
class ClientChangeLog {
        @ChangeSet(id = "CreatCollection", order = "001", author = "MigrationMongock")
    fun changeSet1(mongoDatabase: MongoDatabase) {
        mongoDatabase.createCollection("cliente")
    }
}
