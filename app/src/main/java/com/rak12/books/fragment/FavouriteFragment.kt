package com.rak12.books.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.rak12.books.R
import com.rak12.books.adapter.FavAdapter
import com.rak12.books.database.BookEntity
import com.rak12.books.database.Database


class FavouriteFragment : Fragment() {
    lateinit var rv:RecyclerView
    lateinit var LayoutManager: RecyclerView.LayoutManager
    lateinit var pl: RelativeLayout
    lateinit var favAdapter: FavAdapter
     var booklist=listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view= inflater.inflate(R.layout.fragment_favourite, container, false)
        rv=view.findViewById(R.id.favrecycle)
        pl=view.findViewById(R.id.progresslayoutfav)
        pl.visibility=View.VISIBLE
        LayoutManager=GridLayoutManager(activity as Context,2)
        booklist=Favtask(activity as Context).execute().get()
       // if(activity!=null){
            pl.visibility=View.GONE
        favAdapter=FavAdapter(activity as Context,booklist)
        rv.layoutManager=LayoutManager
        rv.adapter=favAdapter
     //   }




        return view
    }

class  Favtask(val context: Context):AsyncTask<Void,Void,List<BookEntity>>() {

    override fun doInBackground(vararg params: Void?): List<BookEntity> {
        val db = Room.databaseBuilder(context, Database::class.java, "books-db").build()
       return db.bookdao().getallbooks()
    }
}

}