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
            val loadedQuiz = intent.getSerializableExtra("Quiz") as FullQuiz
            playGame(loadedQuiz)
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
        nextQuestion(quiz, 0, 0)
    }

    fun nextQuestion(quiz: FullQuiz, counter:Int, score:Int) {
        val questions = quiz.getQuestions()
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
            checkAnswer(quiz, firstBtn.text as String,correctAnswer, score, counter)
        }

        secondBtn.setOnClickListener{
            checkAnswer(quiz, secondBtn.text as String,correctAnswer, score, counter)
        }

        thirdBtn.setOnClickListener{
            checkAnswer(quiz, thirdBtn.text as String,correctAnswer, score, counter)
        }

        fourthBtn.setOnClickListener{
            checkAnswer(quiz, fourthBtn.text as String,correctAnswer, score, counter)
        }
    }

    fun checkAnswer(quiz: FullQuiz, curAnswer: String, correctAnswer: String, score: Int, counter: Int) {
        val questions = quiz.getQuestions()
        var curScore = score
        var curCounter = counter
        Log.e("Answer", "$curAnswer $correctAnswer")
        if (curAnswer==correctAnswer) curScore += 1
        if (counter!=4) {
            curCounter+=1
            nextQuestion(quiz, curCounter, curScore)
        }
        else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quiz complete!")
                .setMessage("$curScore/5")
                .setPositiveButton("Save"){ dialogInterface: DialogInterface, i: Int ->
                    val curOpener = QuizOpener(applicationContext)
                    curOpener.addData(quiz, applicationContext)

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