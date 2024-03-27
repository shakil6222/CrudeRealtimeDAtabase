package com.example.cruderealtimedatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class UserAdapter(
    val userListModel: ArrayList<UserListModel>,
    var context: Context, private val onClickListener: onUserClickListener
) : RecyclerView.Adapter<UserAdapter.viewHolder>() {

    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameItem = view.findViewById<TextView>(R.id.itemName_TextView)
        val deleteUserIcon = view.findViewById<ImageView>(R.id.deleteUser_icon)
        val updateUserIcon = view.findViewById<ImageView>(R.id.updateUser_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int = userListModel.size


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val userList = userListModel[position]
        holder.nameItem.text = userList.name

        holder.deleteUserIcon.setOnClickListener {
            val dilog = AlertDialog.Builder(context)
            dilog.setTitle("Delete User")
            dilog.setMessage("Are You Delete User")
            dilog.setPositiveButton("Yes") { dilog, which ->
                onClickListener.onDeleteUserData(position)
            }
            dilog.setNegativeButton("No") { dilog, which ->
                dilog.dismiss()
            }

            val alert = dilog.create()
            alert.show()
        }
        holder.updateUserIcon.setOnClickListener {
           onClickListener.onUpdateUserData(position)
        }
    }
}

interface onUserClickListener {
    fun onDeleteUserData(position: Int)
    fun onUpdateUserData(position: Int)
}