package br.com.itau.blacklist.service

import br.com.itau.blacklist.collections.ClienteCollection
import br.com.itau.blacklist.exception.ClienteExistenteException
import br.com.itau.blacklist.exception.NotFoundException
import br.com.itau.blacklist.extention.ClienteResponse
import br.com.itau.blacklist.repository.ClienteRepository
import br.com.itau.blacklist.web.api.ClienteApiDelegate
import br.com.itau.blacklist.web.api.model.ClienteRequest
import br.com.itau.blacklist.web.api.model.ClienteRequestDTO
import br.com.itau.blacklist.web.api.model.ClienteResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ClienteService(
        private val clienteRepository: ClienteRepository
) : ClienteApiDelegate{

    override fun getCliente(cnpj: Int): ResponseEntity<ClienteResponse> =
            ResponseEntity.ok(ClienteResponse(this.buscaCnpjId(cnpj)))

    override fun putCliente(cnpj: Int, clienteRequest: ClienteRequestDTO): ResponseEntity<ClienteResponse>{
        val cliente = buscaCnpjId(cnpj)
        cliente.apply {
            nome = clienteRequest.nome
            dataNascimento =clienteRequest.dataNascimento
            profissao = clienteRequest.profissao
            email = clienteRequest.email
        }
        clienteRepository.save(cliente)
        return  ResponseEntity.ok(ClienteResponse(cliente))
    }

    override fun deleteCliente(cnpj: Int): ResponseEntity<Void> {
        val cliente = buscaCnpjId(cnpj)
        clienteRepository.delete(cliente)
        return ResponseEntity.noContent().build()
    }

    override fun postCliente(clienteRequest: ClienteRequest): ResponseEntity<ClienteResponse>{
        clienteRepository.findById(clienteRequest.cnpj)
                .ifPresent { throw ClienteExistenteException(clienteRequest.cnpj.toString()) }
        val clienteCollection = ClienteCollection(
                cnpj = clienteRequest.cnpj,
                nome = clienteRequest.nome,
                dataNascimento = clienteRequest.dataNascimento,
                profissao = clienteRequest.profissao,
                email = clienteRequest.email
        )

        clienteRepository.save(clienteCollection)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteResponse(clienteCollection))
    }

    fun buscaCnpjId(cnpj: Int): ClienteCollection =
            clienteRepository.findByIdOrNull(cnpj)?: throw NotFoundException("cnpj = $cnpj")

}
