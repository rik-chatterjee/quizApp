package com.example.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.models.Quiz
import com.google.android.material.datepicker.DateSelector
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()

    }

    private fun populateDummyData(){
        quizList.add(Quiz("12-10-2021","12-10-21"))
        quizList.add(Quiz("12-10-2021","12-10-21"))
        quizList.add(Quiz("12-10-2021","12-10-21"))
        quizList.add(Quiz("12-10-2021","12-10-21"))
        quizList.add(Quiz("12-10-2021","12-10-21"))
        quizList.add(Quiz("12-10-2021","12-10-21"))

    }

    private fun setUpView(){
        setUpFireStore()
        setupDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("quizzes").addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            println(value)
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpDatePicker() {
        val btnDatePicker = findViewById<FloatingActionButton>(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DATEPICKER")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yy")
                val date = dateFormatter.format(Date(it))
                Log.d("dateFormat",date)
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setupDrawerLayout() {
        val appBar = findViewById<Toolbar>(R.id.appBar)
        setSupportActionBar(appBar)
        val mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawer)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawerLayout,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener {
            val btnFollowUs = navigationView.menu.findItem(R.id.btnFollowUs)
            val btnProfile = navigationView.menu.findItem(R.id.btnProfile)
            val btnRateUs = navigationView.menu.findItem(R.id.btnRateUs)
            val intent = when(it){
                btnProfile -> Intent(this, ProfileActivity::class.java)
                btnFollowUs -> null
                btnRateUs -> null
                else ->  throw Exception("no path exists")
            }
            startActivity(intent)
            val mainDrawer = findViewById<DrawerLayout>(R.id.mainDrawer)
            mainDrawer.closeDrawers()
            true
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        val quizRecyclerView = findViewById<RecyclerView>(R.id.quizRecyclerView)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}