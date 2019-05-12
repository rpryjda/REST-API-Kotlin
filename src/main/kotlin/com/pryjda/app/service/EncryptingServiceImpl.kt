package com.pryjda.app.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.pryjda.app.component.PrivateKeyBean
import com.pryjda.app.model.request.MessageRequestDTO
import com.pryjda.app.model.request.SampleRequestDTO
import com.pryjda.app.model.response.*
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import java.util.Base64
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun main(args: Array<String>) {
    println("HELLO HERE")
}

@Service
class EncryptingServiceImpl(val mapper: ObjectMapper) : EncryptingService {

    override fun hashMessage(message: MessageRequestDTO): MessageResponseDTO =
            MessageResponseDTO(DigestUtils.sha256Hex(message.message))

    override fun encryptAesUserBody(message: UserResponseDTO, keyAES: String): String {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val messageString: String = mapper.writeValueAsString(message)
        println("messageString: " + messageString)
        val encryptedByteArray: ByteArray = cipher.doFinal(messageString.toByteArray(StandardCharsets.UTF_8))
        val encryptedByteArrayAsBase64: ByteArray = Base64.getEncoder().encode(encryptedByteArray)
        return String(encryptedByteArrayAsBase64, StandardCharsets.UTF_8)
    }

    override fun decryptAesUserBody(message: String, keyAES: String): UserResponseDTO {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val encryptedMessageByteArrayAsBase64: ByteArray = message.toByteArray(StandardCharsets.UTF_8)
        val encryptedMessageByteArray: ByteArray = Base64.getDecoder().decode(encryptedMessageByteArrayAsBase64)
        val decryptedByteArray: ByteArray = cipher.doFinal(encryptedMessageByteArray)
        val decryptedMessageString: String = String(decryptedByteArray, StandardCharsets.UTF_8)
        val request = mapper.readValue(decryptedMessageString, UserResponseDTO::class.java)
        return request
    }

    override fun encryptAesAnyBody(message: SampleRequestDTO, keyAES: String): String {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val messageString: String = mapper.writeValueAsString(message)
        println("messageString: " + messageString)
        val encryptedByteArray: ByteArray = cipher.doFinal(messageString.toByteArray(StandardCharsets.UTF_8))
        val encryptedByteArrayAsBase64: ByteArray = Base64.getEncoder().encode(encryptedByteArray)
        return String(encryptedByteArrayAsBase64, StandardCharsets.UTF_8)
    }

    override fun decryptAesAnyBody(message: String, keyAES: String): SampleRequestDTO {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val encryptedMessageByteArrayAsBase64: ByteArray = message.toByteArray(StandardCharsets.UTF_8)
        val encryptedMessageByteArray: ByteArray = Base64.getDecoder().decode(encryptedMessageByteArrayAsBase64)
        val decryptedByteArray: ByteArray = cipher.doFinal(encryptedMessageByteArray)
        val decryptedMessageString: String = String(decryptedByteArray, StandardCharsets.UTF_8)
        val request = mapper.readValue(decryptedMessageString, SampleRequestDTO::class.java)
        return request
    }

    @Autowired
    val privateKeyBean: PrivateKeyBean? = null

    override fun encryptAESMessage(message: String, keyAES: String): String {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(Charsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedByteArray: ByteArray = cipher.doFinal(message.toByteArray(Charsets.UTF_8))
        println("encryptedByteArray: " + encryptedByteArray.asList())
        println("Base64.getEncoder().encode(encryptedByteArray): " + Base64.getEncoder().encode(encryptedByteArray))
        println("String(Base64.getEncoder().encode(encryptedByteArray): " + String(Base64.getEncoder().encode(encryptedByteArray)))
        return String(Base64.getEncoder().encode(encryptedByteArray))
    }

    override fun decryptAESMessage(message: String, keyAES: String): String {
        val secretKeySpec = SecretKeySpec(keyAES.toByteArray(Charsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val encryptedMessageByteArrayAsBase64: ByteArray = message.toByteArray(Charsets.UTF_8)
        val encryptedMessageByteArray: ByteArray = Base64.getDecoder().decode(encryptedMessageByteArrayAsBase64)
        val decryptedByteArray: ByteArray = cipher.doFinal(encryptedMessageByteArray)
        return String(decryptedByteArray)
    }

    override fun encryptRSAMessage(message: String, publicKeyBase64String: String): EncryptedMessageResponseDTO {

//        val publicKeyBytes: ByteArray = publicKeyBase64String.toByteArray(Charsets.UTF_8)
//        val publicKeyBytesAsBase64 = Base64.getDecoder().decode(publicKeyBytes)
        val publicKeyBytesAsBase64 = Base64.getDecoder().decode(publicKeyBase64String)
//        val spec = X509EncodedKeySpec(publicKeyBytesAsBase64)
        val spec = X509EncodedKeySpec(publicKeyBytesAsBase64)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey: PublicKey = keyFactory.generatePublic(spec)

//        val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        val cipher: Cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedMessageBytes: ByteArray = cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8))
//        val encryptedMessageBytesAsBase64: ByteArray = Base64.getEncoder().encode(encryptedMessageBytes)
//        return EncryptedMessageResponseDTO(encryptedMessageBytesAsBase64)
        return EncryptedMessageResponseDTO(encryptedMessageBytes)
    }

    /*override fun decryptRSAMessage(message: String, privateKeyBase64String: String): String {

        val privateKeyBytes: ByteArray = privateKeyBase64String.toByteArray(Charsets.UTF_8)
        val privateKeyBytesAsBase64: ByteArray = Base64.getDecoder().decode(privateKeyBytes)
//        val spec = X509EncodedKeySpec(privateKeyBytesAsBase64)
        val spec = PKCS8EncodedKeySpec(privateKeyBytesAsBase64)
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey: PrivateKey = keyFactory.generatePrivate(spec)

        val cipher: Cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decryptedMessageBytesAsBase64: ByteArray = cipher.doFinal(message.toByteArray(Charsets.UTF_8))
        val decryptedMessageBytes: ByteArray = Base64.getDecoder().decode(decryptedMessageBytesAsBase64)
        return String(decryptedMessageBytes)
    }*/

    override fun decryptRSAMessage(message: ByteArray): DecryptedMessageResponseDTO {
        val cipher: Cipher = Cipher.getInstance("RSA")
//        cipher.init(Cipher.DECRYPT_MODE, privateKeyBean.privateKey)
        cipher.init(Cipher.DECRYPT_MODE, privateKeyBean?.privateKey)
//        val decryptedMessageBytesAsBase64: ByteArray = cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8))
//        val decryptedMessageBytes: ByteArray = Base64.getDecoder().decode(decryptedMessageBytesAsBase64)
        val decryptedMessageBytes: ByteArray = cipher.doFinal(message)
//        val decryptedMessageBytesAsBase64: ByteArray = cipher.doFinal(decryptedMessageBytes)

//        return DecryptedMessageResponseDTO(String(decryptedMessageBytesAsBase64, StandardCharsets.UTF_8))
        return DecryptedMessageResponseDTO(String(decryptedMessageBytes, StandardCharsets.UTF_8))
    }

    override fun generateKeys(): KeysResponseDTO {
        val keyGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(2048)
        val keyPair: KeyPair = keyGenerator.genKeyPair()

        val publicKey: PublicKey = keyPair.public
        val privateKey: PrivateKey = keyPair.private

        val publicKeyBytes: ByteArray = publicKey.encoded
        val privateKeyBytes: ByteArray = privateKey.encoded

        val publicKeyBytesAsBase64: ByteArray = Base64.getEncoder().encode(publicKeyBytes)
        val privateKeyBytesAsBase64: ByteArray = Base64.getEncoder().encode(privateKeyBytes)
        return KeysResponseDTO(
                String(publicKeyBytesAsBase64, Charsets.UTF_8),
                String(privateKeyBytesAsBase64, Charsets.UTF_8)
        )
//        val publicKeyString: String = Base64.getEncoder().encodeToString(publicKeyBytes)
//        val privateKeyString: String = Base64.getEncoder().encodeToString(privateKeyBytes)
//        return KeysResponseDTO(
//                String(privateKeyBytesAsBase64, Charsets.UTF_8),
//                String(publicKeyBytesAsBase64, Charsets.UTF_8)
//        )
    }

    override fun generatePublicKey(): PublicKeyResponseDTO {
        val keyGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(2048)
        val keyPair: KeyPair = keyGenerator.genKeyPair()

        val publicKey: PublicKey = keyPair.public
        val privateKey: PrivateKey = keyPair.private
        privateKeyBean?.privateKey = privateKey
        val publicKeyBytes: ByteArray = publicKey.encoded
//        val publicKeyBytesAsBase64: ByteArray = Base64.getEncoder().encode(publicKeyBytes)
//        return PublicKeyResponseDTO(String(publicKeyBytesAsBase64, Charsets.UTF_8))

        val publicKeyBytesAsBase64String: String = Base64.getEncoder().encodeToString(publicKeyBytes)
        return PublicKeyResponseDTO(publicKeyBytesAsBase64String)
    }


    //    @GetMapping("/keys")
    //    fun getKeys(): KeysResponseDTO {
    //        val keyGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
    //        keyGenerator.initialize(2048)
    //        val keyPair: KeyPair = keyGenerator.genKeyPair()
    //
    //        val publicKey: PublicKey = keyPair.public
    //        val privateKey: PrivateKey = keyPair.private
    //
    ////        val encodedPublicKey: String = Base64.getEncoder().encodeToString(publicKey.encoded)
    ////        val encodedPrivateKey: String = Base64.getEncoder().encodeToString(privateKey.encoded)
    //
    //        val encodedPrivateKey: ByteArray = Base64.getEncoder().encode(privateKey.encoded)
    //        val encodedPublicKeyUTF_8String: String = String(encodedPublicKey, Charsets.UTF_8)
    //        val encodedPrivateKeyUTF_8String: String = String(encodedPrivateKey, Charsets.UTF_8)
    //        return KeysResponseDTO(encodedPublicKeyUTF_8String, encodedPrivateKeyUTF_8String)
    //    }
}