package com.example.madcamp_week01

import android.net.Uri
import androidx.room.*

@Entity(tableName = "tb_contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true) val id:Long,
    var image: Uri?,
    var name: String,
    var tag: String,
    var tel: String
)