package com.example.quizapp

import android.util.Log
import junit.framework.Assert.assertEquals
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class ApiHandler(addition: String,
                 baseUrl: String,
                 themeName: String,
                 diffculty: String) {
    var addition = addition
    var baseUrl = baseUrl
    var themeName:String = themeName
    var difficultyValue:String = diffculty
    var quiz = FullQuiz()

    /*
    * Fetches the data from the api using retrofit
    * @param       curData            return for the stored data
    * @return      StoredData
    **/
    fun runAPI(curData: StoreData) {
        //Log.e("running", "running")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)

        retrofit.getQuestions(baseUrl+addition).enqueue(object : Callback<QuestionResponse> {

            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {
                Log.e("Response", baseUrl+addition)
                var curQuiz = FullQuiz()
                val questionList: MutableList<QuizQuestion> =  ArrayList()
                for (i in 0..4) {
                    val curQuestion = QuizQuestion()
                    curQuestion.makeQuestion(
                        response.body()?.results!![i].question,
                        response.body()?.results!![i].correct_answer,
                        response.body()?.results!![i].incorrect_answers
                    )
                    questionList.add(curQuestion)
                }
                curQuiz.MakeQuiz(themeName,difficultyValue,questionList)
                curData.storedData(curQuiz)
            }

            override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                Log.e("Error", baseUrl+addition)
            }

        })
    }

    data class QuestionResponse(val results: List<TempQuestion>)
    data class TempQuestion(val question: String, var category: String, var correct_answer: String, var incorrect_answers: List<String>)

    /*
    * Interface to control the extenstion of the API call
    * @param       url       String of the entire url
    **/
    interface UserService {
        //@GET("/api.php?amount=2&type=multiple")
        //fun getUsers(): Call<UserResponse>

        @GET
        fun getQuestions(@Url url: String?): Call<QuestionResponse>
    }
}