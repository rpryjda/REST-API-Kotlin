package com.pryjda.app.service

import com.pryjda.app.model.request.MessageRequestDTO
import com.pryjda.app.model.request.SampleRequestDTO
import com.pryjda.app.model.response.*

interface EncryptingService {

    fun encryptAESMessage(message: String, keyAES: String): String
    fun decryptAESMessage(message: String, keyAES: String): String
    fun encryptRSAMessage(message: String, publicKey: String): EncryptedMessageResponseDTO
    //    fun decryptRSAMessage(message: String, privateKey: String): String
    fun decryptRSAMessage(message: ByteArray): DecryptedMessageResponseDTO

    fun generateKeys(): KeysResponseDTO
    fun generatePublicKey(): PublicKeyResponseDTO

    fun encryptAesAnyBody(message: SampleRequestDTO, keyAES: String): String
    fun decryptAesAnyBody(message: String, keyAES: String): SampleRequestDTO

    fun encryptAesUserBody(message: UserResponseDTO, keyAES: String): String
    fun decryptAesUserBody(message: String, keyAES: String): UserResponseDTO

    fun hashMessage(message: MessageRequestDTO): MessageResponseDTO
}