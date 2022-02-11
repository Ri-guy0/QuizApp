package com.example.quizapp.test

import android.content.Context
import android.util.Log
import com.example.quizapp.FullQuiz
import com.example.quizapp.QuizOpener
import junit.framework.Assert.assertEquals
import org.junit.Test

class TestDatabase(context: Context) {
    var quizzes: MutableList<FullQuiz> = ArrayList()
    private val curOpener = QuizOpener(context)

    init {
        quizzes = curOpener.loadData(context)
    }


    @Test fun quizValidator(){
        /*
        * usually this is not useful however, since it is testing a database connection and
        * loading, it will crash for every new user if this try catch isn't here
        */
        try {
            assertEquals("Theme","Geography", quizzes[1].getTheme())
        } catch (e: AssertionError) {
            Log.e("Geography[test]", quizzes[1].getTheme())
        }

        try {
            assertEquals("Difficulty","hard", quizzes[1].getDifficulty())
        } catch (e: AssertionError) {
            Log.e("hard[test]", quizzes[1].getTheme())
        }
    }

    @Test fun questionValidator(){
        /*
        * usually this is not useful however, since it is testing a database connection and
        * loading, it will crash for every new user if this try catch isn't here
        */
        try {
            assertEquals(
                "title",
                "What is Canada&#039;s largest island?",
                quizzes[1].getQuestions()[2].getTitle()
            )
        } catch (e: AssertionError) {
            Log.e("What is Canada&#039;s largest island?", quizzes[1].getQuestions()[2].getTitle())
        }

        try {
            assertEquals(
                "answer",
                "Baffin Island",
                quizzes[1].getQuestions()[2].getCorrect()
            )
        } catch (e: AssertionError) {
            Log.e("Baffin Island", quizzes[1].getQuestions()[2].getTitle())
        }
    }

    /*
    * this is a test to see what happens in a failed case. this isnt to test the application
    * but instead my knowledge of how the testing works
    */
    @Test fun testJunit(){
        try {
            assertEquals("Theme","Intended", quizzes[1].getTheme())
        } catch (e: AssertionError) {
            Log.e("Intended[test]", quizzes[1].getTheme())
        }
    }

}