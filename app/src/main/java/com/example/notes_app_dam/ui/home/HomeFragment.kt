package com.example.notes_app_dam.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notes_app_dam.NoteModel
import com.example.notes_app_dam.NotesDBHelper
import com.example.notes_app_dam.R
import kotlinx.android.synthetic.main.add_note.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {

    lateinit var notesDBHelper: NotesDBHelper
    var id_note_entry: Int = 0

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.add_note, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }


//    fun fa_mancare() {
//        // REQUEST google.ro - 5s
//        homeViewModel.fa_mancare();
//    }
}
