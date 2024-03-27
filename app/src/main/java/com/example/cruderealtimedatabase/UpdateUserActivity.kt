package com.example.cruderealtimedatabase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var updateUserName:EditText
    private lateinit var updateButton:AppCompatButton
    private lateinit var dataBaseReferences :DatabaseReference
    private var userId : String?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)


        updateUserName = findViewById(R.id.updateName_EditText)
        updateButton = findViewById(R.id.updateButton_Button)

        dataBaseReferences = FirebaseDatabase.getInstance().getReference("Users")
        userId = intent.getStringExtra("id_key")
        val updateName = intent.getStringExtra("userName_key")

        updateUserName.setText(updateName)

        updateButton.setOnClickListener {
           updateUserData()
        }

    }


    private fun updateUserData(){
        val updatename = updateUserName.text.toString()
        val updateUser = UserListModel(userId,updatename)
        dataBaseReferences.child(userId!!).setValue(updateUser)
            .addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                Toast.makeText(this, "Update User Data Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Update UserData Failed", Toast.LENGTH_SHORT).show()
            }
    }
}