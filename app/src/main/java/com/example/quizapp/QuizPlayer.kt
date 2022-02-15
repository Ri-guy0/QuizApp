package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.quizapp.api.ApiHandler
import com.example.quizapp.api.StoreData
import com.example.quizapp.database.QuizOpener
import com.example.quizapp.localclasses.FullQuiz


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

        // changes how to obtain the data (API or list) depending on where the user came from
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

    /*
    * Medium to play the game with the async function for the API call
    * @param       apiHandle                the handler to obtain the quiz from the api
    * @calls       playGame
    **/
    fun gameAPI(apiHandler: ApiHandler) {
        apiHandler.runAPI(object: StoreData {
            override fun storedData(curQuiz: FullQuiz) {
                playGame(curQuiz)
            }

        })
    }

    /*
    * Starts the game
    * @param       quiz                   the quiz used in the current game
    * @call        nextQuestion           calls the first question
    **/
    fun playGame(quiz: FullQuiz) {
        nextQuestion(quiz, 0, 0)
    }

    /*
    * Recursive functionality to display the questions for the quiz
    * @param       quiz                   the quiz used in the current game
    * @param       counter                the question number
    * @param       score                  the current score
    * @call        checkAnswer
    **/
    fun nextQuestion(quiz: FullQuiz, counter:Int, score:Int) {
        val questions = quiz.getQuestions()
        val correctAnswer = questions[counter].getCorrect()
        val incorrectAnswer = questions[counter].getIncorrect()
        var answerList = listOf<String>(correctAnswer, incorrectAnswer[0], incorrectAnswer[1], incorrectAnswer[2])
        answerList = answerList.shuffled() //randomizes order of the buttons

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
            checkAnswer(quiz, firstBtn,correctAnswer, score, counter)
        }

        secondBtn.setOnClickListener{
            checkAnswer(quiz, secondBtn,correctAnswer, score, counter)
        }

        thirdBtn.setOnClickListener{
            checkAnswer(quiz, thirdBtn,correctAnswer, score, counter)
        }

        fourthBtn.setOnClickListener{
            checkAnswer(quiz, fourthBtn,correctAnswer, score, counter)
        }
    }

    /*
    * Checks the current question for correctness and if quiz is complete
    * @param       quiz                   the quiz used in the current game
    * @param       curAnswer              the user chosen answer
    * @param       correctAnswer          the correct answer
    * @param       counter                the question number
    * @param       score                  the current score
    * @call        nextQuestion
    * @view        AlertDialog            gives user choice to save or not when done quiz
    **/
    fun checkAnswer(quiz: FullQuiz, curButton: Button, correctAnswer: String, score: Int, counter: Int) {
        var curAnswer = curButton.text as String;
        val questions = quiz.getQuestions()
        var curScore = score
        var curCounter = counter
        Log.e("Answer", "$curAnswer $correctAnswer")



        if (curAnswer==correctAnswer) {
            curButton.setBackgroundColor(Color.GREEN)

            curScore += 1
        } else {
            curButton.setBackgroundColor(Color.RED)

        }

        if (counter!=4) {
            try {
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    curCounter+=1
                    curButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                    nextQuestion(quiz, curCounter, curScore)
                }, 1000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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