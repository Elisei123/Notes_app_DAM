package com.example.notes_app_dam

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration


@RequiresApi(Build.VERSION_CODES.O)

class EditNoteActivity : AppCompatActivity() {

    lateinit var notesDBHelper: NotesDBHelper
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        notesDBHelper = NotesDBHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note_activity)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val edit_text_from_edit_note: EditText = findViewById(R.id.edit_text_from_edit_note)
        val save_edit_note: Button = findViewById(R.id.save_edit_note)

        //To pick information from MainActivity.kt (note.message)
        val text_to_edit = intent.getStringExtra("noteMessage");
        val id_item = intent.getIntExtra("id_item", -1);
        edit_text_from_edit_note.setText(text_to_edit, TextView.BufferType.EDITABLE)

        save_edit_note.setOnClickListener {
            println(edit_text_from_edit_note.text)
            var result = notesDBHelper.modify_text(edit_text_from_edit_note.text.toString(), id_item)
            Toast.makeText(this, "Notita a fost modificata", Toast.LENGTH_SHORT).show()
            this.recreate();
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
