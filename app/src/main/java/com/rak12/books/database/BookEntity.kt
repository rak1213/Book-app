package com.rak12.books.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey @ColumnInfo(name = "book_id") val bookid:Int,
    @ColumnInfo(name = "book_name")var bookname:String,
    @ColumnInfo(name = "book_author") var author:String,
    @ColumnInfo(name = "book_rating")  var rating:String,
    @ColumnInfo(name = "book_price") var price:String,
    @ColumnInfo(name = "book_info") val bookinfo:String,
    @ColumnInfo(name = "book_img") var img:String


)
