package com.example.notes_app_dam

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.add_note.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.notes_app_dam.NoteModel
import com.example.notes_app_dam.NotesDBHelper

@RequiresApi(Build.VERSION_CODES.O)

class MainActivity : AppCompatActivity() {

    lateinit var notesDBHelper: NotesDBHelper
    private lateinit var appBarConfiguration: AppBarConfiguration
    var id_note_entry: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // P.S: Il poti folosi la add_note. -> acea iconica din dreapta jos cu mesaj
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        notesDBHelper = NotesDBHelper(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.add_note, R.id.see_notes, R.id.search_note), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // showAllNotes(View) // De adaugat ca sa porneasca cand vad galeria cu notes..
    }

    fun addNote(v:View){
        // pick date and hour
        val current = LocalDateTime.now()
        val formatter_date = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted_date = current.format(formatter_date)

        val formatter_hour = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formatted_hour = current.format(formatter_hour)

        var note_date = formatted_date.toString()
        var note_hour = formatted_hour.toString()
       // Pick message from .xml
        var note_message1: EditText = findViewById(R.id.edit_text_message)
        var note_message = note_message1.text.toString()

        var result = notesDBHelper.insertNote(NoteModel(note_date = note_date,note_hour = note_hour, note_message=note_message))
        println(result)
        this.edit_text_message.setText("")
        Toast.makeText(this, "Notita a fost salvata.", Toast.LENGTH_LONG).show()
    }

    fun showAllNotes(){
        var notes = notesDBHelper.readAllNotes()

        var ll_entries: LinearLayout = findViewById(R.id.ll_entries)
        notes.forEach {

//            val button = Button(this)
//            button.apply{
//                text = "X"
//                layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT)
//            }

            var tv_user = TextView(this)
            tv_user.textSize = 15F
            tv_user.text = it.note_message.toString() + "\n(" + it.note_date.toString() + " - " + it.note_hour.toString() + ").\n\n\n"
            tv_user.setPadding(50, 10, 20, 10)
            ll_entries.addView(tv_user)
            //ll_entries.addView(button)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
