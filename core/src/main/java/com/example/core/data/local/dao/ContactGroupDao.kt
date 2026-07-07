package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.ContactGroup

@Dao
interface ContactGroupDao {
    @Insert
    suspend fun insertGroup(group: ContactGroup): Long

    @Update
    suspend fun updateGroup(group: ContactGroup)

    @Query("SELECT * FROM contact_groups ORDER BY createdAt DESC")
    fun getAllGroups(): kotlinx.coroutines.flow.Flow<List<ContactGroup>>

    @Query("SELECT * FROM contact_groups WHERE id = :groupId")
    suspend fun getGroupById(groupId: Long): ContactGroup?

    @Query("UPDATE contact_groups SET contactCount = contactCount + 1 WHERE id = :groupId")
    suspend fun incrementContactCount(groupId: Long)

    @Query("UPDATE contact_groups SET contactCount = contactCount - 1 WHERE id = :groupId AND contactCount > 0")
    suspend fun decrementContactCount(groupId: Long)
}