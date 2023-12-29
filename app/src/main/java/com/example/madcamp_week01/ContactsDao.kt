package com.example.madcamp_week01

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface ContactsDao {
    @Query("SELECT * FROM tb_contacts")
    fun getAll(): List<Contacts>

    @Insert
    fun insertAll(vararg contacts: Contacts)

    @Delete
    fun delete(contacts: Contacts)
}