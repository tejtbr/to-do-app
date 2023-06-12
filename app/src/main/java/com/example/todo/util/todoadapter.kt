package com.example.todo.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.EachTodoItemBinding

class todoadapter(private val list:MutableList<tododata>) :RecyclerView.Adapter<todoadapter.toDoViewHolder>() {


    private var listener:ToDoAdapterClickInterface?=null
    fun setListener(listener:ToDoAdapterClickInterface){
        this.listener=listener
    }



    inner class toDoViewHolder(val binding:EachTodoItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): toDoViewHolder {
        val binding=EachTodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return toDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: toDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.todoTask.text=this.task
                binding.deleteTask.setOnClickListener {
                    listener?.ondelete(this)
                }

                binding.editTask.setOnClickListener {
                    listener?.onedite(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ToDoAdapterClickInterface{
        fun ondelete(tododata:tododata)
        fun onedite(tododata:tododata)
    }
}