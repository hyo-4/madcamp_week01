package com.example.madcamp_week01

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.concurrent.Flow

@Dao
interface ContactsDao {

    @Query("SELECT * FROM tb_contacts ORDER BY name ASC")
    fun getAll(): List<Contacts>

    @Insert
    fun insertAll(vararg contacts: Contacts)

    @Delete
    fun delete(contacts: Contacts)

    @Update
    fun update(contacts: Contacts)

    @Query("SELECT * FROM tb_contacts WHERE name LIKE :searchQuery OR tel LIKE :searchQuery")
    fun searchContact(searchQuery:String): List<Contacts>
}