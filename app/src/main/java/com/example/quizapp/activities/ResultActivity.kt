package com.example.quizapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import com.example.quizapp.R
import com.google.gson.Gson
import com.example.quizapp.models.Quiz


class ResultActivity : AppCompatActivity() {

    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val txtAnswer = findViewById<TextView>(R.id.txtAnswer)
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        val txtScore = findViewById<TextView>(R.id.txtScore)
        var score = 0
        val count = quiz.questions.entries
        Log.d("COUNT",count.toString())
        for (entry in quiz.questions.entries) {
            val question = entry.value
            Log.d("entry",question.toString())
            if (question.answer == question.userAnswer) {
                Log.d("SCORE",score.toString())
                score += 10
                Log.d("SCORE",score.toString())
            }
        }

        Log.d("SCORE",score.toString())
        txtScore.text = "Your score : $score"
    }
}