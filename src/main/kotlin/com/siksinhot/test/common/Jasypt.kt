package com.siksinhot.test.common

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.siksinhot.test.server.env.Env
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig

object Jasypt {
    private const val algorithm = "PBEWithMD5AndDES"
    var password: String = Env.jasyptPassword
    fun encode(text: String?): String {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.password = password; // 암호화 키 값, 서버의 환경변수로 설정 추천
        config.algorithm = algorithm; // 알고리즘
        config.setKeyObtentionIterations("1000")
        config.setPoolSize("1")
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.stringOutputType = "base64"
        encryptor.setConfig(config)
        return encryptor.encrypt(text)
    }

    fun decode(encText: String): String {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.password = password; // 암호화 키 값, 서버의 환경변수로 설정 추천
        config.algorithm = algorithm; // 알고리즘
        config.setKeyObtentionIterations("1000")
        config.setPoolSize("1")
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.stringOutputType = "base64"
        encryptor.setConfig(config)
        return if (encText.startsWith("ENC(")) {
            val tmp = encText.replace("ENC\\(".toRegex(), "").replace("\\)".toRegex(), "")
            encryptor.decrypt(tmp)
        } else {
            encryptor.decrypt(encText)
        }
    }

    fun phoneNumber(phoneNumberStr: String?): String? {
        return when(phoneNumberStr) {
            null -> null
            else -> {
                val phonenumber = PhoneNumberUtil.getInstance().parse(phoneNumberStr, "KR")
                PhoneNumberUtil.getInstance().format(phonenumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
            }
        }
    }

//    @JvmStatic
//    fun main(argv: Array<String>) {
//        password = argv[0]
//        println("db user[prod] : " + encode("twinkorea"))
//        println("db pass[prod] : " + encode("Twin2023!Korea"))
//    }
}
