package com.example.cruderealtimedatabase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(),onUserClickListener {

    //    axes realtime database
    lateinit var dataBaseReference: DatabaseReference
    private lateinit var recycleView: RecyclerView
    private lateinit var addflotingButton: FloatingActionButton
    private lateinit var userList: ArrayList<UserListModel>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView = findViewById(R.id.recycleList_view)
        addflotingButton = findViewById(R.id.addFloating_button)
        recycleView.layoutManager = LinearLayoutManager(this)
        userList = ArrayList<UserListModel>()
        addflotingButton.setOnClickListener {
            startActivity(Intent(this, InsertDataLayout::class.java))
        }
        getUserList()

    }

    private fun getUserList() {
        dataBaseReference = FirebaseDatabase.getInstance().getReference("Users")
        dataBaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val userData = userSnap.getValue(UserListModel::class.java)
                        userList.add(userData!!)

                    }
                    val userAdapter = UserAdapter(userList,this@MainActivity,this@MainActivity)
                    recycleView.adapter = userAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onDeleteUserData(position: Int) {
        val userId = userList[position].id
        if (userId != null) {
            dataBaseReference.child(userId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted User Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Deleted Data Failed", Toast.LENGTH_SHORT).show()
                }
        }
        else{
            Toast.makeText(this, "Empty Data", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onUpdateUserData(position: Int) {
        val intent = Intent(this,UpdateUserActivity::class.java)
        intent.putExtra("id_key",userList[position].id)
        intent.putExtra("userName_key",userList[position].name)
        startActivity(intent)
    }
}