package com.rak12.books.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.VoiceInteractor
import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rak12.books.R
import com.rak12.books.adapter.DashboardAdapter
import com.rak12.books.model.Book
import com.rak12.books.util.ConnectionManager
import org.json.JSONException
import java.util.ArrayList

class DashboardFragment : Fragment() {
    lateinit var recyclerdash: RecyclerView
    lateinit var layoutmanager: RecyclerView.LayoutManager
    lateinit var pl:RelativeLayout


               var booklist= arrayListOf<Book>()

    lateinit var recycleradapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?                                                                                       
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
         pl =view.findViewById(R.id.progresslayout)
        pl.visibility=View.VISIBLE
        recyclerdash = view.findViewById(R.id.recyclerdashboard)
        layoutmanager = LinearLayoutManager(activity)

           val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books"
          if (ConnectionManager().checkconnectivity(activity as Context)) {
              val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                     // print("response $it")
                  try {
                           pl.visibility=View.GONE

                      val success = it.getBoolean("success")
                      if (success) {
                          val data = it.getJSONArray("data")
                          for (i in 0 until data.length()) {
                              val bookinfo = data.getJSONObject(i)
                              val bookobject = Book(
                                  bookinfo.getInt("book_id"),
                                  bookinfo.getString("name"),
                                  bookinfo.getString("author"),
                                  bookinfo.getString("rating"),
                                  bookinfo.getString("price"),
                                  bookinfo.getString("image")
                              )
                              booklist.add(bookobject)

                              recycleradapter = DashboardAdapter(activity as Context, booklist)


                              recyclerdash.adapter = recycleradapter
                              recyclerdash.layoutManager = layoutmanager
                          }
                      }
                      else{
                          Toast.makeText(activity as Context,"ERROR",Toast.LENGTH_LONG).show()
                      }
                  }
                  catch (e:JSONException){
                   Toast.makeText(activity as Context,"ERROR1212",Toast.LENGTH_LONG).show()

                  }
                  },
                      Response.ErrorListener {
                            Toast.makeText(activity as Context,"Volley ERROR",Toast.LENGTH_LONG).show()
                      }) {
                      override fun getHeaders(): MutableMap<String, String> {
                          val headers = HashMap<String, String>()
                          headers["Content-type"] = "application/json"
                          headers["token"] = "b239d60302e428"
                          return headers
                      }
              }
              queue.add(jsonObjectRequest)
          } else{

                 val alert = AlertDialog.Builder(activity as Context)
              alert.setTitle("Error")
                        alert.setMessage("INTERNET connection not found")
                        alert.setPositiveButton("open settings") { text, listener ->
                            val i=Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            startActivity(i)
                            activity?.finish()


                        }
                        alert.setNegativeButton("exit") { text, listener ->
                            ActivityCompat.finishAffinity(activity as Activity)

                        }
                        alert.create().show()



          }
        return view

    }
}




