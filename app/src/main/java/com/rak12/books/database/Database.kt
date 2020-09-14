package com.rak12.books.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookEntity::class],version = 1)
abstract class Database:RoomDatabase() {
    abstract fun bookdao():Dao
}