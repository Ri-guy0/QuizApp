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

        // listener to go to the next page
        toModBtn.setOnClickListener{
            val toModIntent = Intent(this, QuizModifier::class.java)
            startActivity(toModIntent)
        }

        // gets the data from the database
        val curOpener = QuizOpener(applicationContext)
        quizzes = curOpener.loadData(applicationContext)

        // set the list adapter for the ListView
        quizAdapter= QuizAdapter(this, quizzes)
        quizzesView.adapter=quizAdapter


        /*
        * Gives option to delete the quiz from the database
        * @param       adapterView
        * @param       view1
        * @param       i                  the position in the list of the item clicked
        * @param       l
        * @view        AlertDialog        gives option to accept deletion
        **/
        quizzesView.setOnItemLongClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val curId = quizAdapter!!.getItemId(i)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete quiz?")
                .setMessage("Would you like to delete ${quizzes[i].getTheme()}:${curId}")
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, j: Int ->
                    val curOpener = QuizOpener(applicationContext)
                    curOpener.deleteQuiz(curId, applicationContext)
                    quizzes.removeAt(i)
                    quizAdapter!!.notifyDataSetChanged()
                }
                .setNegativeButton("No"){ dialogInterface: DialogInterface, j: Int ->

                }
                .create().show()
            true
        }

        /*
        * Gives option to play the quiz from the database
        * @param       parent
        * @param       view
        * @param       position           the position in the list of the item clicked
        * @param       id
        * @view        AlertDialog        gives option to accept playing the quiz
        **/
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
            val difficultyText = newView.findViewById<TextView>(R.id.QuizDifficulty)

            themeText.text = quizList[position].getTheme()
            difficultyText.text = quizList[position].getDifficulty()
            return newView
        }

    }
}