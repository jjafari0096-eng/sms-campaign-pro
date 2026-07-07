package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<Contact>): List<Long>

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE groupId = :groupId")
    suspend fun deleteContactsByGroup(groupId: Long)

    @Query("SELECT * FROM contacts ORDER BY createdAt DESC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE groupId = :groupId ORDER BY createdAt DESC")
    fun getContactsByGroup(groupId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE phone = :phone LIMIT 1")
    suspend fun getContactByPhone(phone: String): Contact?

    @Query("SELECT COUNT(*) FROM contacts")
    fun getTotalContacts(): Flow<Int>

    @Query("SELECT COUNT(*) FROM contacts")
    fun getContactCount(): Flow<Int> = getTotalContacts()

    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :searchQuery || '%' OR phone LIKE '%' || :searchQuery || '%'")
    fun searchContacts(searchQuery: String): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE tags LIKE '%' || :tag || '%'")
    fun getContactsByTag(tag: String): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id IN (:ids)")
    suspend fun getContactsByIds(ids: List<Long>): List<Contact>

    @Query("DELETE FROM contacts WHERE id IN (:ids)")
    suspend fun deleteMultipleContacts(ids: List<Long>)
}