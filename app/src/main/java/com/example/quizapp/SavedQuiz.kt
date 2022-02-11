package com.example.quizapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.quizapp.database.QuizOpener
import com.example.quizapp.localclasses.FullQuiz
import java.io.Serializable

class SavedQuiz : AppCompatActivity() {
    var quizzes: MutableList<FullQuiz> = ArrayList()
    var quizAdapter: QuizAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_quiz)

        val toModBtn = findViewById<Button>(R.id.ToQuizMod)
        val quizzesView = findViewById<ListView>(R.id.SavedQuizList)

        // Tool Bar
        val myToolBar = findViewById<Toolbar>(R.id.QuizBar)
        setSupportActionBar(myToolBar)
        myToolBar.setBackgroundColor(Color.parseColor("#3F67DA"))

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
            //quizzes[position].PrintQuiz()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_quiz_bar, menu)
        return true
    }

    /*
     * Method is responsible for the toolbar selection
     * Pressing help icon brings up a alert dialog
     * @param       item        the item that was clicked on the toolbar
     * @return      true
     * @see         alert dialogue
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.helpItem -> {
                val alertDialogBuilder = AlertDialog.Builder(this)

                // sets alert dialog to display help
                alertDialogBuilder.setTitle("Help:")
                    .setMessage("Tap the quiz to enter it \nHold click to delete it")
                    .setPositiveButton("Okay") { click: DialogInterface?, arg: Int ->
                    }
                alertDialogBuilder.create().show()
            }
        }
        return true
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