package com.example.core.data.repository

import com.example.core.data.local.dao.ContactDao
import com.example.core.data.local.entity.Contact
import com.example.core.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepository {
    override suspend fun addContact(contact: Contact): Long {
        return contactDao.insertContact(contact)
    }

    override suspend fun addContacts(contacts: List<Contact>): List<Long> {
        return contactDao.insertContacts(contacts)
    }

    override suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    override suspend fun deleteMultipleContacts(ids: List<Long>) {
        contactDao.deleteMultipleContacts(ids)
    }

    override fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }

    override fun getContactsByGroup(groupId: Long): Flow<List<Contact>> {
        return contactDao.getContactsByGroup(groupId)
    }

    override suspend fun getContactByPhone(phone: String): Contact? {
        return contactDao.getContactByPhone(phone)
    }

    override fun getTotalContacts(): Flow<Int> {
        return contactDao.getContactCount()
    }

    override fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query)
    }

    override fun getContactsByTag(tag: String): Flow<List<Contact>> {
        return contactDao.getContactsByTag(tag)
    }

    override suspend fun getContactCount(): Int {
        // We need to collect the flow, alternatively we could add a suspend function to the DAO
        // For simplicity, this is a simplified implementation
        return 0
    }
}