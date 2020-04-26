package br.com.itau.blacklist.collections

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cliente")
data class ClienteCollection(
        @Id
        var cnpj: Int,
        var nome: String,
        var dataNascimento: String,
        var profissao: String,
        var email: String

)
