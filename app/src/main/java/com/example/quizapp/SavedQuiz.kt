package com.example.quizapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.io.Serializable

class SavedQuiz : AppCompatActivity() {
    var quizzes: MutableList<FullQuiz> = ArrayList()
    var quizAdapter: QuizAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_quiz)

        val toModBtn = findViewById<Button>(R.id.ToQuizMod)
        val quizzesView = findViewById<ListView>(R.id.SavedQuizList)

        toModBtn.setOnClickListener{
            val toModIntent = Intent(this, QuizModifier::class.java)
            startActivity(toModIntent)
        }

        val curOpener = QuizOpener(applicationContext)
        quizzes = curOpener.loadData(applicationContext)

        quizAdapter= QuizAdapter(this, quizzes)
        quizzesView.adapter=quizAdapter


        quizzesView.setOnItemLongClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val curId = quizAdapter!!.getItemId(i)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete quiz?")
                .setMessage("Would you like to delete ${quizzes[i].getTheme()}:${curId}")
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                    val curOpener = QuizOpener(applicationContext)
                    curOpener.deleteQuiz(curId, applicationContext)
                    quizzes.removeAt(i)
                }
                .setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

                }
                .create().show()
            true
        }

        quizzesView.setOnItemClickListener { parent, view, position, id ->
            quizzes[position].PrintQuiz()
            val curId = quizAdapter!!.getItemId(position)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter quiz?")
                .setMessage("Would you like to enter ${quizzes[position].getTheme()}:${curId}")
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                    val toQuizIntent = Intent(this, QuizPlayer::class.java).apply {
                        putExtra("Quiz", quizzes[position] as Serializable)
                        putExtra("GetAPI", "false")
                    }
                    startActivity(toQuizIntent)
                }
                .setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

                }
                .create().show()
            true
        }
    }

    class QuizAdapter(
        val context: Context,
        private val quizList: MutableList<FullQuiz>
    ): BaseAdapter(){
        override fun getCount(): Int {
            return quizList.size
        }

        override fun getItem(position: Int): Any {
            return quizList[position]
        }

        override fun getItemId(position: Int): Long {
            return quizList[position].getId()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val newView = LayoutInflater.from(context).inflate(R.layout.quiz_list, parent, false)
            val themeText = newView.findViewById<TextView>(R.id.QuizTheme)

            themeText.text = quizList[position].getTheme()
            return newView
        }

    }
}