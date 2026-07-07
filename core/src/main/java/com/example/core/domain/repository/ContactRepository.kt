package com.example.core.domain.repository

import com.example.core.data.local.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun addContact(contact: Contact): Long
    suspend fun addContacts(contacts: List<Contact>): List<Long>
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun deleteMultipleContacts(ids: List<Long>)
    fun getAllContacts(): Flow<List<Contact>>
    fun getContactsByGroup(groupId: Long): Flow<List<Contact>>
    suspend fun getContactByPhone(phone: String): Contact?
    fun getTotalContacts(): Flow<Int>
    fun searchContacts(query: String): Flow<List<Contact>>
    fun getContactsByTag(tag: String): Flow<List<Contact>>
    suspend fun getContactCount(): Int
}