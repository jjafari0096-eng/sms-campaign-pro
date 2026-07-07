package com.example.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String,
    val company: String? = null,
    val city: String? = null,
    val tags: String? = null, // Comma separated tags
    val groupId: Long? = null,
    val customFields: String? = null, // JSON string of custom fields
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)