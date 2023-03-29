package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView(){
        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {
        val appBar = findViewById<Toolbar>(R.id.appBar)
        setSupportActionBar(appBar)
        val mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawer)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawerLayout,R.string.app_name,R.string.app_name)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}