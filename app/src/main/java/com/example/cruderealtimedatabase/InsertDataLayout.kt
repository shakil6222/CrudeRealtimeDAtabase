package com.example.cruderealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertDataLayout : AppCompatActivity() {
    private lateinit var editUserName: EditText
    private lateinit var addButton: Button
    private lateinit var dataBaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data_layout)

        // Initialize Firebase Database reference to "Users" node
        dataBaseReference = FirebaseDatabase.getInstance().getReference("Users")
        editUserName = findViewById(R.id.editUserName_EditText)
        addButton = findViewById(R.id.uploadUser_button)

        addButton.setOnClickListener {
            insertUsers()
        }

    }

    private fun insertUsers() {
        val getUserId = dataBaseReference.push().key
        val getUserName = editUserName.text.toString()
        val userModel = UserListModel(getUserId!!, getUserName)

        dataBaseReference.child(getUserId).setValue(userModel)
            .addOnSuccessListener {
                Toast.makeText(this, "User Dada Add", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "User Add Data Failed", Toast.LENGTH_SHORT).show()
            }
    }

}