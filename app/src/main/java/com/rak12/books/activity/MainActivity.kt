package com.rak12.books.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.rak12.books.*
import com.rak12.books.fragment.AboutFragment
import com.rak12.books.fragment.DashboardFragment
import com.rak12.books.fragment.FavouriteFragment
import com.rak12.books.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var dl:DrawerLayout
    lateinit var nv:NavigationView
    lateinit var fl:FrameLayout
    lateinit var tl:androidx.appcompat.widget.Toolbar
    lateinit var cl:CoordinatorLayout
    var previousitem :MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dl=findViewById(R.id.drawer)
        nv=findViewById(R.id.navigationdrawer)
        fl=findViewById(R.id.frame)
        tl=findViewById(R.id.toolbar)
        cl=findViewById(R.id.cord)
        setuptoolbar()
     opendasboard()
        val actionbartoggle=ActionBarDrawerToggle(this,dl,
            R.string.opendrawer,
            R.string.closedrawer
        )
        dl.addDrawerListener(actionbartoggle)
        actionbartoggle.syncState()
        nv.setNavigationItemSelectedListener {
            if(previousitem!=null){
 previousitem?.isChecked=false
            }

            it.isCheckable=true
            it.isChecked=true
            previousitem = it
            when(it.itemId){

               R.id.dashboard -> {opendasboard()

                dl.closeDrawers()
               }
                R.id.fav ->{supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame,
                        FavouriteFragment()
                    )
                    .commit()
                    supportActionBar?.title="Favourites"
                    dl.closeDrawers()
                }
                R.id.profile ->{supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame,
                        ProfileFragment()
                    )

                    .commit()
                    supportActionBar?.title="Profile"
                dl.closeDrawers()}
                R.id.abt ->{supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame,
                        AboutFragment()
                    )

                    .commit()
                    supportActionBar?.title="About App"
                    dl.closeDrawers()
                }
            }



            return@setNavigationItemSelectedListener true }
    }

    fun setuptoolbar(){
        setSupportActionBar(tl)
        supportActionBar?.title="Book Hub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
        {
            dl.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun opendasboard(){
        supportFragmentManager.beginTransaction().replace(
            R.id.frame,
            DashboardFragment()
        ).commit()
        supportActionBar?.title="Dashboard"
        nv.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
        if(frag!= DashboardFragment())
        {
            opendasboard()
        }
        else{

            super.onBackPressed()
    }
    }
}