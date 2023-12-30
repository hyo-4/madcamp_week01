package com.example.madcamp_week01

import androidx.room.*

@Entity(tableName = "tb_contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true) val id:Long,
    var image: Int,
    var name: String,
    var tel: String
)