package com.example.notes_app_viewmodel

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var rvAdapter: NoteAdapter
    lateinit var mainViewModel: MainViewModel  //ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//ViewModel>>
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getNotes().observe(this, {
                notes -> rvAdapter.update(notes)
        })

        note_btn.setOnClickListener {
            if(note_et.text.toString() !=""){
                mainViewModel.addNote(note_et.text.toString())
                note_et.text.clear()
                note_et.clearFocus()
            }else{
                Toast.makeText(this,"note is empty",Toast.LENGTH_SHORT)
            }

        }

        rvAdapter = NoteAdapter(this)
        note_recyclerView.adapter = rvAdapter
        note_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun raiseDialog(id: Int,note:String){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = note
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> mainViewModel.editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}