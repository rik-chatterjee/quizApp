package com.example.quizapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // populateDummyData()
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
        setUpFirestore()
        setupDrawerLayout()
        setUpRecyclerView()
    }

    private fun setUpFirestore() {
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("quizzes").addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
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