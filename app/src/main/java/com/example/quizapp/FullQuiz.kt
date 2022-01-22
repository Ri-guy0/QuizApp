package com.example.quizapp

import android.util.Log
import java.io.Serializable

class FullQuiz : Serializable  {
    private var _id = 0L
    private var themeName = ""
    private var difficulty = ""
    private var questionList: List<QuizQuestion> = listOf()

    fun MakeQuiz (themeName: String, difficulty: String, questionList: List<QuizQuestion>){
        this.themeName=themeName
        this.difficulty=difficulty
        this.questionList=questionList
    }

    fun makeQuiz (_id: Long, themeName: String, difficulty: String, questionList: List<QuizQuestion>){
        this._id=_id
        this.themeName=themeName
        this.difficulty=difficulty
        this.questionList=questionList
    }

    /*
    * prints the entire quiz
    * @debug          displays the entire quiz
    **/
    fun PrintQuiz (){
        for (element in questionList) {
            element.printQuestion()
        }
    }

    fun printQuizSimple(){
        Log.e("id", _id.toString())
        Log.e("Theme", themeName)
        Log.e("difficulty", difficulty)
        Log.e("Length", questionList.size.toString())
    }

    fun getId(): Long {
        return _id
    }

    fun getTheme(): String{
        return themeName
    }

    fun getDifficulty(): String{
        return difficulty
    }

    fun getQuestions():List<QuizQuestion>{
        return questionList
    }
} 