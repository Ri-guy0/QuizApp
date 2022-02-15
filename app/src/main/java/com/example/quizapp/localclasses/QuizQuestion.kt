package com.example.quizapp.localclasses

import android.util.Log
import java.io.Serializable

class QuizQuestion : Serializable  {
    private var _quizId = 0L
    private var _id = 0L
    private var question = ""
    private var correctAnswer = ""
    private var incorrectAnswers: List<String> = listOf("", "", "")

    fun makeQuestion (question: String, correctAnswer: String, incorrectAnswers: List<String>) {
        this.question=question
        this.correctAnswer=correctAnswer
        this.incorrectAnswers=incorrectAnswers
    }

    fun makeQuestion (_quizId: Long, _id: Long, question: String, correctAnswer: String, incorrectAnswers: List<String>) {
        this._quizId=_quizId
        this._id=_id
        this.question=question
        this.correctAnswer=correctAnswer
        this.incorrectAnswers=incorrectAnswers
    }

    fun printQuestion() {
        Log.e("Q", question)
        Log.e("A", correctAnswer)
        Log.e("W", incorrectAnswers.toString())
    }

    fun getIncorrect(): List<String>{
        return incorrectAnswers
    }

    fun getCorrect(): String{
        return correctAnswer
    }

    fun getTitle(): String{
        return question
    }
}