package com.rak12.books.activity

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rak12.books.R
import com.rak12.books.database.BookEntity
import com.rak12.books.database.Database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    lateinit var bookname: TextView
    lateinit var author: TextView
    lateinit var price: TextView
    lateinit var rating: TextView
    lateinit var bookingo: TextView
    lateinit var pl: RelativeLayout
    lateinit var pb: ProgressBar
    lateinit var img: ImageView
    lateinit var favbtn: Button

        lateinit var tb:Toolbar
    var bookid: Int? = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        bookname = findViewById(R.id.bookname)

        author = findViewById(R.id.authorname)
        favbtn = findViewById(R.id.fav)
        price = findViewById(R.id.price)
        rating = findViewById(R.id.rating)
       tb=findViewById(R.id.toolbar)

        bookingo = findViewById(R.id.bookinfo)
        img = findViewById(R.id.img)
        //    pb=findViewById(R.id.)
        pl = findViewById(R.id.progresslayout)
       setSupportActionBar(tb)
        supportActionBar?.title="Book Details"


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        pl.visibility = View.VISIBLE
        if (intent != null) {
            bookid = intent.getIntExtra("book_id", 100)


        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        if (bookid == 100) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

        }
        val queue = Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookid)
        val jsonRequest = @SuppressLint("ResourceAsColor")
        object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {
            try {
                val success: Boolean = it.getBoolean("success")
                if (success) {
                    pl.visibility = View.GONE
                    val data = it.getJSONObject("book_data")
                    Picasso.get().load(data.getString("image")).into(img)
                    bookname.text = data.getString("name")
                    author.text = data.getString("author")
                    price.text = data.getString("price")
                    rating.text = data.getString("rating")
                    bookingo.text = data.getString("description")
                    val img = data.getString("image")
                    val bookEntity = BookEntity(
                        bookid?.toInt() as Int,
                        bookname.text.toString(),
                        author.text.toString(),
                        rating.text.toString(), price.text.toString(), bookingo.text.toString(),
                        img
                    )
                    val checkfav = DBAsnctask(applicationContext, bookEntity, 1).execute()
                    val isfav = checkfav.get()
                    if (isfav) {
                        favbtn.text = "REMOVE FROM FAVOURITES"
                        favbtn.setBackgroundResource(R.color.nofav)
                    } else {
                        favbtn.text = "ADD TO FAVOURITES"
                        favbtn.setBackgroundResource(R.color.colorPrimary)
                    }
                    favbtn.setOnClickListener {
                    if (!DBAsnctask(applicationContext, bookEntity, 1).execute().get()) {
                        val addtofav = DBAsnctask(applicationContext, bookEntity, 2).execute().get()
                        if (addtofav) {
                            Toast.makeText(this, "ADDED TO FAVS", Toast.LENGTH_SHORT).show()
                            favbtn.text = "REMOVE FROM FAVOURITES"
                            favbtn.setBackgroundResource(R.color.nofav)
                        } else {
                            Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        val removefav =
                            DBAsnctask(applicationContext, bookEntity, 3).execute().get()
                        if (removefav) {
                            Toast.makeText(this, "REMOVED FROM FAVS", Toast.LENGTH_SHORT).show()
                            favbtn.text = "ADD TO FAVOURITES"
                            favbtn.setBackgroundResource(R.color.colorPrimary)
                        }
                        else {
                            Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()

                        }


                    }
                    }


                } else {
                    Toast.makeText(this, "Error1213", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                Toast.makeText(this, "Error1212", Toast.LENGTH_SHORT).show()

            }
        },
            Response.ErrorListener {
                Toast.makeText(this, "Volley Error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["token"] = "b239d60302e428"
                return headers

            }
        }
        queue.add(jsonRequest)
    }

    class DBAsnctask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, Database::class.java, "books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {

                1 -> {
                    val book: BookEntity? = db.bookdao().getbookbyid(bookEntity.bookid)
                    db.close()
                    return book != null

                }

                2 -> {
                    db.bookdao().insertbook(bookEntity)
                    db.close()
                    return true

                }
                3 -> {
                    db.bookdao().deletebook(bookEntity)
                    db.close()
                    return true

                }
            }

            return false
        }

    }
}
