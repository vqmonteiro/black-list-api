package br.com.itau.blacklist

import br.com.itau.blacklist.collections.ClienteCollection
import br.com.itau.blacklist.config.MongoContainerSetup
import br.com.itau.blacklist.repository.ClienteRepository
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [MongoContainerSetup::class])
class ClienteIntegratedTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var clienteRepository: ClienteRepository

    @BeforeEach
    fun clearEntities() {
        clienteRepository.deleteAll()
    }

    @Test
    fun getClienteTest(){
        val clienteEnvio = gerarCliente(1)
        clienteRepository.save(clienteEnvio)

        val resultJson = this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/cliente/${clienteEnvio.cnpj}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString

        val json = JSONObject(resultJson)
        val clienteCollection = clienteRepository.findAll()[0]

        assertEquals(clienteCollection.cnpj, json.getInt("cnpj"))
        assertEquals(clienteCollection.dataNascimento, json.getString("dataNascimento"))
        assertEquals(clienteCollection.email, json.getString("email"))
        assertEquals(clienteCollection.nome, json.getString("nome"))
        assertEquals(clienteCollection.profissao, json.getString("profissao"))
    }

    @Test
    fun getClienteNotFoundTest(): ResultActions =
            this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/cliente/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)

    private fun gerarCliente(index: Int): ClienteCollection =
            ClienteCollection(
                    cnpj = index,
                    nome = index.toString(),
                    dataNascimento = index.toString(),
                    profissao = index.toString(),
                    email = index.toString()
            )

}
