package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdt : EditText
    lateinit var noteDescriptionEdit : EditText
    lateinit var addupdateBtn : Button
    lateinit var viewModal : NoteViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idEdtNoteDescriptin)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            addupdateBtn.setText("Nota Atualizada")
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        }else{
            addupdateBtn.setText("Nota Salva")
        }

        addupdateBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteID
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this, "Nota Atualizada" , Toast.LENGTH_LONG).show()
                }
            }else{
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "Nota Adicionada", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(application, MainActivity::class.java))
            this.finish()
        }
    }
}