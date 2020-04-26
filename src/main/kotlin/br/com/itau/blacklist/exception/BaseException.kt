package br.com.itau.blacklist.exception

open class BaseException constructor(mensagem : String?): RuntimeException()
{
    @Transient
    var mensagem: String? = mensagem
}
