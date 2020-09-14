package com.rak12.books.adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.rak12.books.R
import com.rak12.books.activity.DescriptionActivity
import com.rak12.books.model.Book
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DashboardAdapter(val context:Context,val data:ArrayList<Book>):RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
      val bookname:TextView=view.findViewById(R.id.bookname)
        val author:TextView=view.findViewById(R.id.authorname)
        val price:TextView=view.findViewById(R.id.price)
        val rating:TextView=view.findViewById(R.id.rating)
        val img:ImageView=view.findViewById(R.id.img)
       val onerow:RelativeLayout=view.findViewById(R.id.onerow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_singlerow_dashboard,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
    return data.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        var info=data[position]
        holder.bookname.text=info.bookname
        holder.author.text=info.author
        holder.price.text=info.price
        holder.rating.text=info.rating
        Picasso.get().load(info.img).error(R.drawable.ic_book).into(holder.img)
       holder.onerow.setOnClickListener {
           val i= Intent(context,DescriptionActivity::class.java)
          i.putExtra("book_id",info.bookid)
      context.startActivity(i)

        }
    }

}