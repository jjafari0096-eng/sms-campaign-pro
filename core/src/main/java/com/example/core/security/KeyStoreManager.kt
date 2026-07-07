package com.example.core.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyStoreManager {
    private val ANDROID_KEYSTORE = "AndroidKeyStore"
    private val DB_KEY_ALIAS = "sms_campaign_db_key"
    
    fun getDatabasePassphrase(context: Context): ByteArray {
        val masterKey = getOrCreateMasterKey(context)
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        var passphrase = sharedPreferences.getString("db_passphrase", null)
        if (passphrase == null) {
            passphrase = generateSecurePassphrase()
            sharedPreferences.edit().putString("db_passphrase", passphrase).apply()
        }
        
        return passphrase.toByteArray()
    }
    
    private fun getOrCreateMasterKey(context: Context): MasterKey {
        return try {
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        } catch (e: Exception) {
            generateKeyInKeystore()
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        }
    }
    
    private fun generateKeyInKeystore() {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            DB_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }
    
    private fun generateSecurePassphrase(): String {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        
        val secretKey = if (keyStore.containsAlias(DB_KEY_ALIAS)) {
            keyStore.getKey(DB_KEY_ALIAS, null) as SecretKey
        } else {
            generateKeyInKeystore()
            keyStore.getKey(DB_KEY_ALIAS, null) as SecretKey
        }
        
        return secretKey.encoded.joinToString("") { String.format("%02x", it) }
    }
}