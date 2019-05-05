package com.pryjda.app.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pryjda.app.component.PrivateKeyBean
import com.pryjda.app.model.request.SampleRequestDTO
import com.pryjda.app.model.request.EncryptedMessageRequestDTO
import com.pryjda.app.model.request.EncryptingRequestDTO
import com.pryjda.app.model.response.DecryptedMessageResponseDTO
import com.pryjda.app.model.response.EncryptedMessageResponseDTO
import com.pryjda.app.model.response.KeysResponseDTO
import com.pryjda.app.model.response.PublicKeyResponseDTO
import com.pryjda.app.service.EncryptingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EncryptingController(val encryptingService: EncryptingService,
                           val mapper: ObjectMapper,
                           val privateKeyBean: PrivateKeyBean) {

    val keyAES: String = "wojneojwenvkdwnKNKNEZK;9Y938R102"

    @PostMapping("/encrypt/AES")
    fun encryptAES(@RequestBody message: String): String =
            encryptingService.encryptAESMessage(message, keyAES)

    @PostMapping("/decrypt/AES")
    fun decryptAES(@RequestBody message: String): String =
            encryptingService.decryptAESMessage(message, keyAES)

    @PostMapping("/encrypt/RSA")
    fun encryptRSA(@RequestBody message: EncryptingRequestDTO): EncryptedMessageResponseDTO =
            encryptingService.encryptRSAMessage(message.message, message.key)

//    @PostMapping("/decrypt/RSA")
//    fun decryptRSA(@RequestBody message: EncryptingRequestDTO): String =
//            encryptingService.decryptRSAMessage(message.message, message.key)

    @PostMapping("/decrypt/RSA")
    fun encryptRSA(@RequestBody message: EncryptedMessageRequestDTO): DecryptedMessageResponseDTO =
            encryptingService.decryptRSAMessage(message.message)

    @GetMapping("/keys")
    fun getKeys(): KeysResponseDTO =
            encryptingService.generateKeys()

    @GetMapping("/public-key")
    fun getPublicKey(): PublicKeyResponseDTO =
            encryptingService.generatePublicKey()

    @PostMapping("/encrypt-request")
    fun encryptAesObject(@RequestBody message: SampleRequestDTO/*, responseHTTP: HttpServletResponse*/): String =
            encryptingService.encryptAesAnyBody(message, keyAES)
//        {
//        val answer: String = encryptingService.encryptAesAnyBody(message, keyAES)
//        val sign: String = encryptingService.encryptRSAMessage(mapper.writeValueAsString(message), encryptingService.generatePublicKey().publicKey )
//        responseHTTP.setHeader("security-sign", "my sign")
//        return answer
//    }

    @PostMapping("/decrypt-request")
    fun decryptAesObject(@RequestBody message: String): SampleRequestDTO =
            encryptingService.decryptAesAnyBody(message, keyAES)
}