package com.rak12.books.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rak12.books.R
import com.rak12.books.database.BookEntity
import com.squareup.picasso.Picasso

class FavAdapter(val context: Context,val booklist:List<BookEntity>):RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    class FavViewHolder(view: View): RecyclerView.ViewHolder(view){
        val bookname: TextView =view.findViewById(R.id.txtFavBookTitle)
        val author: TextView =view.findViewById(R.id.txtFavBookAuthor)
        val price: TextView =view.findViewById(R.id.txtFavBookPrice)
        val rating: TextView =view.findViewById(R.id.txtFavBookRating)
        val img: ImageView =view.findViewById(R.id.imgFavBookImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_singlefavrow,parent,false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
       return booklist.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
   var data=booklist[position]
        holder.bookname.text=data.bookname.toString()
        holder.author.text=data.author.toString()
        holder.price.text=data.price.toString()
        holder.rating.text=data.rating
        Picasso.get().load(data.img).into(holder.img)
    }
}