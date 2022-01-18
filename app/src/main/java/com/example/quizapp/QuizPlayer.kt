package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class QuizPlayer : AppCompatActivity() {
    var addition = "/api.php?amount=5&type=multiple"
    var baseUrl = "https://opentdb.com/"
    var themeValue:String = ""
    var themeName:String = ""
    var difficultyValue:String = ""
    var isApi:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_player)

        isApi = intent.getStringExtra("GetAPI").toString()

        if (isApi.equals("true")) {
            Log.e("getapi?","true")
            themeValue = intent.getStringExtra("Theme Value").toString()
            themeName = intent.getStringExtra("Theme Name").toString()
            difficultyValue = intent.getStringExtra("Difficulty").toString()
            addition= "$addition&category=$themeValue&difficulty=$difficultyValue"
            val apiHandler = ApiHandler(addition, baseUrl, themeName, difficultyValue)
            gameAPI(apiHandler)
        } else {
            Log.e("getapi?","false")
            // gets from listview
        }

    }

    fun gameAPI(apiHandler: ApiHandler) {
        apiHandler.runAPI(object: StoreData {
            override fun storedData(curQuiz: FullQuiz) {
                playGame(curQuiz)
            }

        })
    }

    fun playGame(quiz: FullQuiz) {
        nextQuestion(quiz.getQuestions(), 0, 0)
    }

    fun nextQuestion(questions: List<QuizQuestion>, counter:Int, score:Int) {
        val correctAnswer = questions[counter].getCorrect()
        val incorrectAnswer = questions[counter].getIncorrect()
        var answerList = listOf<String>(correctAnswer, incorrectAnswer[0], incorrectAnswer[1], incorrectAnswer[2])
        answerList = answerList.shuffled()

        val titleText = findViewById<TextView>(R.id.QuizTitle)
        val firstBtn = findViewById<Button>(R.id.AnswerOne)
        val secondBtn = findViewById<Button>(R.id.AnswerTwo)
        val thirdBtn = findViewById<Button>(R.id.AnswerThree)
        val fourthBtn = findViewById<Button>(R.id.AnswerFour)

        titleText.text = questions[counter].getTitle()
        firstBtn.text = answerList[0]
        secondBtn.text = answerList[1]
        thirdBtn.text = answerList[2]
        fourthBtn.text = answerList[3]

        questions[counter].printQuestion()

        firstBtn.setOnClickListener{
            checkAnswer(questions, firstBtn.text as String,correctAnswer, score, counter)
        }

        secondBtn.setOnClickListener{
            checkAnswer(questions, secondBtn.text as String,correctAnswer, score, counter)
        }

        thirdBtn.setOnClickListener{
            checkAnswer(questions, thirdBtn.text as String,correctAnswer, score, counter)
        }

        fourthBtn.setOnClickListener{
            checkAnswer(questions, fourthBtn.text as String,correctAnswer, score, counter)
        }
    }

    fun checkAnswer(questions: List<QuizQuestion>, curAnswer: String, correctAnswer: String, score: Int, counter: Int) {
        var curScore = score
        var curCounter = counter
        Log.e("Answer", "$curAnswer $correctAnswer")
        if (curAnswer==correctAnswer) curScore += 1
        if (counter!=4) {
            curCounter+=1
            nextQuestion(questions, curCounter, curScore)
        }
        else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quiz complete!")
                .setMessage("$curScore/5")
                .setPositiveButton("Save"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(applicationContext,"Saved",Toast.LENGTH_LONG).show()
                    val toSavedIntent = Intent(this, SavedQuiz::class.java)
                    startActivity(toSavedIntent)
                }
                .setNegativeButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                    val toSavedIntent = Intent(this, SavedQuiz::class.java)
                    startActivity(toSavedIntent)
                }
                .create().show()
            Log.e("Score", "$curScore/5")
        }
    }
}