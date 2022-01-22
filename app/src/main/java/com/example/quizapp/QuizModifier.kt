package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class QuizModifier : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_modifier)

        val themeSpinner = findViewById<Spinner>(R.id.ThemeList)
        val difficultySpinner = findViewById<Spinner>(R.id.DifficultList)
        val toQuizBtn = findViewById<Button>(R.id.ToQuiz)

        // Array Adapter used to insert data into the dropdown menu for the themes
        ArrayAdapter
            .createFromResource(this, R.array.quiz_themes,android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                themeSpinner.adapter = adapter
            }

        // Array Adapter used to insert data into the dropdown menu for the difficulties
        ArrayAdapter
            .createFromResource(this, R.array.quiz_difficulty,android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                difficultySpinner.adapter = adapter
            }

        // Intent to go to the next page with the data from the user
        toQuizBtn.setOnClickListener{
            val toQuizIntent = Intent(this, QuizPlayer::class.java).apply {
                putExtra("Theme Value", ConvertTheme(themeSpinner.selectedItem.toString()).toString())
                putExtra("Difficulty", difficultySpinner.selectedItem.toString())
                putExtra("Theme Name", themeSpinner.selectedItem.toString())
                putExtra("GetAPI", "true")
            }
            startActivity(toQuizIntent)
        }
    }

    /*
    * converts the name of the theme to the index that the api uses
    * @param       themeText                the theme as text
    * @return      Int                      the theme as an index
    **/
    fun ConvertTheme (themeText: String): Int {
        var themeNum = 0

        when (themeText) {
            "General Knowledge" -> themeNum=9
            "Science and Computers" -> themeNum=18
            "Animals" -> themeNum=27
            "Geography" -> themeNum=22
            "Art" -> themeNum=25
            "Mythology" -> themeNum=20
            "History" -> themeNum=23
            "Sports" -> themeNum=21
            "Film" -> themeNum=11
            "TV" -> themeNum=14
            "Politics" -> themeNum=24
        }

        Log.e("num", themeNum.toString())
        return themeNum
    }
}