package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class SavedQuiz : AppCompatActivity() {
    var quizzes: ArrayList<String> = ArrayList()
    var quizAdapter: QuizAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_quiz)

        val toModBtn = findViewById<Button>(R.id.ToQuizMod)
        val quizzesView = findViewById<ListView>(R.id.SavedQuizList)

        quizzes.add("hello")

        toModBtn.setOnClickListener{
            val toModIntent = Intent(this, QuizModifier::class.java)
            startActivity(toModIntent)
        }

        quizAdapter= QuizAdapter(this, quizzes)
        quizzesView.adapter=quizAdapter

    }

    class QuizAdapter(
        val context: Context,
        //val quizList: ArrayList<FullQuiz>
        val quizList: ArrayList<String>
    ): BaseAdapter(){
        override fun getCount(): Int {
            return quizList.size
        }

        override fun getItem(position: Int): Any {
            return quizList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val newView = LayoutInflater.from(context).inflate(R.layout.quiz_list, parent, false)
            val themeText = newView.findViewById<TextView>(R.id.QuizTheme)

            themeText.text = quizList[position]
            return newView
        }

    }
}