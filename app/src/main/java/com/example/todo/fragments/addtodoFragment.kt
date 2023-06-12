package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.databinding.FragmentAddtodoBinding
import com.example.todo.util.tododata
import com.google.android.material.textfield.TextInputEditText


class addtodoFragment : DialogFragment() {
   private lateinit var binding:FragmentAddtodoBinding
   private lateinit var listener:dialognextbtnclicklistener
   private var todoData: tododata?=null



   fun setListener(listener : dialognextbtnclicklistener){
       this.listener=listener
   }

    companion object{
        const val TAG="addtodofragment"

        @JvmStatic
        fun newInstance(taskId: String ,task:String)=addtodoFragment().apply {
            arguments =Bundle().apply {
                putString("taskId",taskId)
                putString("task",task)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddtodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments!=null){
            todoData= tododata(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )

            binding.todoEt.setText(todoData?.task)
        }
        register()
    }

    private fun register(){
        binding.todoNextBtn.setOnClickListener {
            val todotask=binding.todoEt.text.toString()
            if(!todotask.isEmpty()){
                if(todoData==null) {
                    listener.onSaveTask(todotask, binding.todoEt)
                }else{
                    todoData?.task=todotask
                    listener.onupdateTask(todoData!!,binding.todoEt)
                }
            }else{
                Toast.makeText(context,"please type something",Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }



    interface dialognextbtnclicklistener{
        fun onSaveTask(todo :String,todoEt :TextInputEditText)
        fun onupdateTask(todoData: tododata,todoEt :TextInputEditText)
    }

}