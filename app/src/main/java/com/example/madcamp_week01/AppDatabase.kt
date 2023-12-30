package com.example.madcamp_week01

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contacts::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    //미리 만들어 놓은 ContactsDao를 접근할 수 있도록 abstract fun을 이용해서 contactsDao()를 만들어 준다
    companion object {
        //@Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context:Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "memo_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }

}