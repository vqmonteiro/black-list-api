package br.com.itau.blacklist.repository

import br.com.itau.blacklist.collections.ClienteCollection
import org.springframework.data.mongodb.repository.MongoRepository

interface ClienteRepository : MongoRepository<ClienteCollection, Int>
