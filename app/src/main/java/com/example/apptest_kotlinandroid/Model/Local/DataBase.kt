package com.example.apptest_kotlinandroid.Model.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class],version=2)
abstract class DataBase: RoomDatabase() {

    abstract fun daoPosts(): DaoPost

    companion object {

        // Singleton prevents multiple instances of database opening at the

        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "HNMobile database V4"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}