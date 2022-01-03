package com.example.notesappviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var addNoteEt: EditText
    lateinit var addBu : Button
    lateinit var recView: RecyclerView
    private lateinit var rvAdapter: RecyclerViewAdapter
//    private lateinit var notes: List<Note>
    lateinit var viewModel:MyViewModel
    var userInput = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getNotes().observe(this, {
                notes -> rvAdapter.update(notes)
        })
//        notes = listOf()

        addNoteEt = findViewById(R.id.addNoteEt)
        addBu = findViewById(R.id.addBu)
        recView = findViewById(R.id.recView)
        rvAdapter = RecyclerViewAdapter(this)
        recView.adapter = rvAdapter
        recView.layoutManager = LinearLayoutManager(this)
       /// getNotes()

        addBu.setOnClickListener {
            viewModel.addNote(addNoteEt.text.toString())
        }

    }
    fun dilog(updateOrDelete :String,selectedNote:String,pk:Int) {

        if (updateOrDelete.equals("update")){
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Update Note")

            var  input = EditText(this)
            input.setText(selectedNote)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            //performing positive action
            builder.setPositiveButton("update") { dialogInterface, which ->

                userInput = input.text.toString()
                viewModel.editNote(pk,userInput)


            }
            builder.setNegativeButton("CANCEL"){dialogInterface, which ->}
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            // alertDialog.setCancelable(false)
            alertDialog.show()
        }

        else {
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Are you sure to delete note  ")
            var  input = EditText(this)
            input.setText(selectedNote)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            //performing positive action
            builder.setPositiveButton("Delete") { dialogInterface, which ->
                userInput = input.text.toString()
                viewModel.deleteNote(pk)

            }
            builder.setNegativeButton("CANCEL"){dialogInterface, which ->}
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(true)
            alertDialog.show()
        }
    }
}