package br.com.itau.blacklist.extention

import br.com.itau.blacklist.collections.ClienteCollection
import br.com.itau.blacklist.web.api.model.ClienteResponse

fun ClienteResponse(clienteCollection: ClienteCollection): ClienteResponse =
        ClienteResponse()
        .apply {
            cnpj = clienteCollection.cnpj
            nome = clienteCollection.nome
            dataNascimento = clienteCollection.dataNascimento
            profissao = clienteCollection.profissao
            email = clienteCollection.email
        }
