package com.example.quizapp.test

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.example.quizapp.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class TestApi(context: Context) {
    var context= context
    var baseUrl = "https://opentdb.com/"

    @Test
    fun themeValidator_convert(){
        var converter = ThemeConverter()
        var arrayTheme = context.resources.getStringArray(R.array.quiz_themes)

        assertEquals(27, converter.convert(arrayTheme[2]))
        assertEquals(17, converter.convert(arrayTheme[16]))
        assertEquals(23, converter.convert(arrayTheme[6]))
        assertEquals(9, converter.convert(arrayTheme[0]))
        assertEquals(29, converter.convert(arrayTheme[21]))
    }


    @Test
    fun quizValidator(theme: String, difficulty: String){
        var converter = ThemeConverter()
        var addition= "/api.php?amount=5&type=multiple&category=${converter.convert(theme)}&difficulty=${difficulty}"
        val apiHandler = ApiHandler(addition, baseUrl, theme, difficulty)
        apiHandler.runAPI(object: StoreData {
            override fun storedData(curQuiz: FullQuiz) {
                assertEquals("Theme",theme, curQuiz.getTheme())
                assertEquals("Difficulty",difficulty, curQuiz.getDifficulty())
                assertEquals("Questions", 5, curQuiz.getQuestions().size)
            }
        })
    }


}