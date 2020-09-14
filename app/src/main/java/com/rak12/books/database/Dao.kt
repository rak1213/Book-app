package com.rak12.books.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rak12.books.model.Book

@Dao
interface Dao {
    @Insert
    fun insertbook(bookEntity: BookEntity)

    @Delete
     fun deletebook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getallbooks():List<BookEntity>

   @Query("SELECT * FROM books where book_id=:bookid")
    fun getbookbyid(bookid:Int):BookEntity

}