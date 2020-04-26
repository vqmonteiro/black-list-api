package br.com.itau.blacklist.erro

import br.com.itau.blacklist.exception.ClienteExistenteException
import br.com.itau.blacklist.exception.NotFoundException
import br.com.itau.blacklist.web.api.model.ApiErro
import br.com.itau.blacklist.web.api.model.Erro
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ControllerAdvice
class ExceptionTranslator {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun exception(ex: NotFoundException): ApiErro {
        return ApiErro()
                .mensagem("Não encontrado")
                .erros(listOf(
                    Erro()
                        .codigo("ERR-0001")
                        .mensagem("Não encontramos o cliente  ${ex.mensagem}")))
    }

    @ExceptionHandler(ClienteExistenteException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun exception(ex: ClienteExistenteException): ApiErro {
        return ApiErro()
                .mensagem("Cliente ja existente")
                .erros(listOf(
                        Erro()
                                .codigo("ERR-0002")
                                .mensagem("Ja existe um cliente para o cnpj :  ${ex.mensagem}")))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun exception(ex: Exception): ApiErro {
        return ApiErro()
                .mensagem("Erro no servidor")
                .erros(listOf(
                        Erro()
                                .codigo("ERR-0003")
                                .mensagem("Tente novamente mais tarde")))
    }

}
