package com.example.notes_app_dam.ui.gallery

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notes_app_dam.MainActivity
import com.example.notes_app_dam.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (this.activity as MainActivity).showAllNotes()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.see_notes, container, false)
        //val textView: TextView = root.findViewById(R.id.text_gallery)
        // var listView: ListView = root.findViewById(R.id.listView)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it

        })
        return root
    }
}
