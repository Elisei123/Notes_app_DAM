package com.example.notes_app_dam

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.AdapterView
import android.widget.Spinner
import kotlinx.android.synthetic.main.add_note.*

@RequiresApi(Build.VERSION_CODES.O)

class MainActivity : AppCompatActivity() {

    lateinit var notesDBHelper: NotesDBHelper
    private lateinit var appBarConfiguration: AppBarConfiguration

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
                R.id.add_note, R.id.see_notes, R.id.search_note, R.id.edit_text_from_edit_note), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    fun addNote(v:View){
        // pick date and hour
        val current = LocalDateTime.now()
        val formatter_date = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted_date = current.format(formatter_date)

        val formatter_hour = DateTimeFormatter.ofPattern("HH:mm") // hours and minuts.
        val formatted_hour = current.format(formatter_hour)

        var note_date = formatted_date.toString()
        var note_hour = formatted_hour.toString()
       // Pick message from .xml
        var note_message1: EditText = findViewById(R.id.edit_text_message)
        var note_message = note_message1.text.toString()


        // Am pus noteid = 0 pt ca este cu AUTOINCREMENT, si vreau sa folosesc id-ul.
        var result = notesDBHelper.insertNote(NoteModel(noteid = 0, note_date = note_date,note_hour = note_hour, note_message=note_message))

        var text_edit_text : EditText = findViewById(R.id.edit_text_message)
        text_edit_text.setText(" ")
        text_edit_text.hint = "Scrie notita!"
        Toast.makeText(this, "Notita a fost salvata.", Toast.LENGTH_SHORT).show()
    }
    // delete note fun when you clicked X button.
    fun delete_note(button: Button){
        //println("Vrei sa stergi notita cu numarul: " + button.id)
        notesDBHelper.deleteUser(button.id)
    }

    // show All notes in "Vezi notitele."
    fun showAllNotes(){
        var notes = notesDBHelper.readAllNotes()

        var ll_entries: LinearLayout = findViewById(R.id.ll_entries)
        notes.forEach {


            // create delete button.
            val button = Button(this)
            button.apply{
                id=it.noteid
                text = "Sterge task."
                layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
            }

            // create edit button.
            val button_for_edit_text = Button(this)
            button_for_edit_text.apply {
                id=it.noteid
                text = "Modifica task."
                layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
            }

            var tv_user = TextView(this)
            tv_user.textSize = 15F
            tv_user.text = "\n\n" + it.note_message.toString() + "\n(" + it.note_date.toString() + " - " + it.note_hour.toString() + ")."
            tv_user.setPadding(50, 10, 20, 10)
            ll_entries.addView(tv_user)
            ll_entries.addView(button)
            ll_entries.addView(button_for_edit_text)

            button.setOnClickListener {
                delete_note(button)
                Toast.makeText(this, "Notita a fost stearsa.", Toast.LENGTH_SHORT).show()
                ll_entries.removeAllViews()
                showAllNotes()
            }
            val mesaj: String = it.note_message.toString()
            button_for_edit_text.setOnClickListener{
                edit_note(mesaj, button.id)
            }
        }
    }

    fun edit_note(noteMessage: String, id_item: Int) {
        val intent = Intent(this, EditNoteActivity::class.java).apply {
            putExtra("noteMessage",  noteMessage)
            putExtra("id_item", id_item)
        }
        startActivity(intent)
    }

    // Search menu.
    fun show_on_activity(notes: ArrayList<NoteModel>){
        var ll_entries_search: LinearLayout = findViewById(R.id.ll_entries_search)
        ll_entries_search.removeAllViews()
        notes.forEach {
            var tv_note = TextView(this@MainActivity)
            tv_note.textSize = 15F
            tv_note.text = "\n" + it.note_message.toString() + "\n(" + it.note_date.toString() + " - " + it.note_hour.toString() + ")."
            tv_note.setPadding(50, 10, 20, 10)
            ll_entries_search.addView(tv_note)
        }
    }

    fun search_note(){
        var edit_text_search_note: EditText = findViewById(R.id.edit_text_search_note)
        var spinner: Spinner = findViewById(R.id.spinner_option_search)
        var search_button: Button = findViewById(R.id.search_button)

        val options = arrayOf("Cuvinte", "Data", "Ora")
        val adapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
                edit_text_search_note.hint = "Selecteaza tipul."
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (options.get(position) == "Cuvinte"){
                    edit_text_search_note.hint = "Scrie cuvinte cheie."

                    search_button.setOnClickListener {
                        var notes = notesDBHelper.readNotes_words(edit_text_search_note.text.toString())
                        show_on_activity(notes)
                    }
                }else if(options.get(position) == "Data"){
                    edit_text_search_note.hint = "21.04.2020"

                    search_button.setOnClickListener {
                        var notes = notesDBHelper.readNotes_date(edit_text_search_note.text.toString())
                        show_on_activity(notes)
                    }
                }else if(options.get(position) == "Ora"){
                    edit_text_search_note.hint = "23:00"

                    search_button.setOnClickListener {
                        var notes =
                            notesDBHelper.readNotes_hour(edit_text_search_note.text.toString())
                        show_on_activity(notes)
                    }
                }
            }
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
