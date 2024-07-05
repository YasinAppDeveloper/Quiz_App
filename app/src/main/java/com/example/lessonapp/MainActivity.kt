package com.example.lessonapp

import QuestionAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firestore: FirebaseFirestore
    private var questions: List<Question> = listOf()
    private var userAnswers: IntArray = intArrayOf()
    private lateinit var userComments: Array<String?>
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Load questions from Firestore
        loadQuestions()

        // floating action button click pass data to display activity
        findViewById<FloatingActionButton>(R.id.fab_submit).setOnClickListener {
            if (areAllQuestionsAnswered()) {
                val intent = Intent(this, DisplayActivity::class.java)
                intent.putExtra("questions", ArrayList(questions))
                intent.putExtra("userAnswers", userAnswers)
                intent.putExtra("userComments", userComments)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please answer all questions before submitting.", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // Share app button
        binding.share.setOnClickListener {
            shareAppLink()
        }

        // Close app button
        binding.finish.setOnClickListener {
            onBackPressed()
        }
    }

    // load question in database firestore
    private fun loadQuestions() {
        firestore.collection("Question").get()
            .addOnSuccessListener { result ->
                questions = result.toObjects(Question::class.java)
                userAnswers = IntArray(questions.size) {
                    -1
                }
                userComments = Array(questions.toList().size) {
                    null
                }
                setupRecyclerView()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load questions: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    // setup recyclerView
    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = QuestionAdapter(questions, userAnswers, userComments)
    }

    // condition check all answer fill
    private fun areAllQuestionsAnswered(): Boolean {
        return userAnswers.all { it != -1 }
    }

    // share app
    private fun shareAppLink() {
        val appLink = "https://play.google.com/store/apps/details?id=com.yourapp.package"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this app: $appLink")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share app via"))
    }
}






