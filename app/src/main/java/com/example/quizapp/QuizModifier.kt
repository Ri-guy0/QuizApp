package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class QuizModifier : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_modifier)

        val themeSpinner = findViewById<Spinner>(R.id.ThemeList)
        val difficultySpinner = findViewById<Spinner>(R.id.DifficultList)
        val toQuizBtn = findViewById<Button>(R.id.ToQuiz)

        // Tool Bar
        val myToolBar = findViewById<Toolbar>(R.id.QuizBar)
        setSupportActionBar(myToolBar)
        myToolBar.setBackgroundColor(Color.parseColor("#3F67DA"))

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
            "Math" -> themeNum=19
            "Books" -> themeNum=10
            "Vehicle" -> themeNum=28
            "Music" -> themeNum=12
            "Video Games" -> themeNum=15
            "Nature" -> themeNum=17
            "Anime" -> themeNum=31
            "Board Games" -> themeNum=16
            "Cartoon" -> themeNum=32
            "Gadget" -> themeNum=30
            "Comic" -> themeNum=29
        }

        Log.e("num", themeNum.toString())
        return themeNum
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_quiz_bar, menu)
        return true
    }

    /*
     * Method is responsible for the toolbar selection
     * Pressing help icon brings up a alert dialog
     * @param       item        the item that was clicked on the toolbar
     * @return      true
     * @see         alert dialogue
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.helpItem -> {
                val alertDialogBuilder = AlertDialog.Builder(this)

                // sets alert dialog to display help
                alertDialogBuilder.setTitle("Help:")
                    .setMessage("User the dropdown menus to change the difficulty and theme!\n")
                    .setPositiveButton("Okay") { click: DialogInterface?, arg: Int ->
                    }
                    .setNegativeButton("Open Site") { click: DialogInterface?, arg: Int ->
                        val toWebpage = Intent(Intent.ACTION_VIEW)
                        toWebpage.data = Uri.parse("https://opentdb.com/")
                        startActivity(toWebpage)
                    }
                alertDialogBuilder.create().show()
            }
        }
        return true
    }
}