package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import com.example.quizapp.test.TestApi
import com.example.quizapp.test.TestDatabase
import com.example.quizapp.test.TestHub

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toSavedBtn = findViewById<Button>(R.id.AcceptTos)
        val tosText = findViewById<TextView>(R.id.TosText)
        val privacyText = findViewById<TextView>(R.id.PrivacyText)

        tosText.movementMethod = ScrollingMovementMethod();
        privacyText.movementMethod = ScrollingMovementMethod();

        val toSavedIntent = Intent(this, SavedQuiz::class.java).apply {
            putExtra("Test", "test sent")
        }

        toSavedBtn.setOnClickListener{
            startActivity(toSavedIntent)
        }

        var tests = TestHub(applicationContext)
    }
}