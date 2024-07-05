package com.example.lessonapp

import ResultAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonapp.databinding.ActivityDisplayBinding

@Suppress("OVERRIDE_DEPRECATION")
class DisplayActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDisplayBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // get data in mainActivity
        val questions = intent.getParcelableArrayListExtra<Question>("questions")!!
        val userAnswers = intent.getIntArrayExtra("userAnswers")!!
        val userComments = intent.getStringArrayExtra("userComments")!!

        // setup recyclerview
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ResultAdapter(questions, userAnswers, userComments)


        // back to mainActivity
        binding.backBtnArrow.setOnClickListener {
            restartQuiz()
        }
    }

    // start quiz
    private fun restartQuiz() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // onBackPress method
    override fun onBackPressed() {
        super.onBackPressed()
        restartQuiz()
    }
}