package com.example.quizapp

import android.util.Log

class ThemeConverter {
    /*
    * converts the name of the theme to the index that the api uses
    * @param       themeText                the theme as text
    * @return      Int                      the theme as an index
    **/
    fun convert (themeText: String): Int {
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

        //Log.e("num", themeNum.toString())
        return themeNum
    }
}