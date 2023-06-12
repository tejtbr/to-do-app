package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.databinding.FragmentSigninBinding
import com.example.todo.util.todoadapter
import com.example.todo.util.tododata
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(), addtodoFragment.dialognextbtnclicklistener,
    todoadapter.ToDoAdapterClickInterface {


    private lateinit var auth:FirebaseAuth
    private lateinit var databaseref: DatabaseReference
    private lateinit var navcontroller: NavController
    private lateinit var binding: FragmentHomeBinding
    private var popupfrag:addtodoFragment?=null

    private lateinit var adapter:todoadapter
    private lateinit var mlist:MutableList<tododata>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getdatafromdatabase()
        registerevents()
    }

    private fun init(view:View){
        navcontroller=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
        databaseref=FirebaseDatabase.getInstance().reference.child("task")
            .child(auth.currentUser?.uid.toString())

        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager=LinearLayoutManager(context)
        mlist= mutableListOf()
        adapter= todoadapter(mlist)
        adapter.setListener(this)
        binding.mainRecyclerView.adapter=adapter

    }

    private fun getdatafromdatabase(){
        databaseref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mlist.clear()
                for(taskSnapShot in snapshot.children){
                    val todotask=taskSnapShot.key?.let{
                        tododata(it,taskSnapShot.value.toString())
                    }
                    if(todotask!=null){
                        mlist.add(todotask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun registerevents(){
        binding.addTaskBtn.setOnClickListener {
            if(popupfrag!=null){
                childFragmentManager.beginTransaction().remove(popupfrag!!).commit()
            }
            popupfrag=addtodoFragment()
            popupfrag!!.setListener(this)
            popupfrag!!.show(
                childFragmentManager,
                addtodoFragment.TAG
            )
        }
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
       databaseref.push().setValue(todo).addOnCompleteListener{
           if(it.isSuccessful){
               Toast.makeText(context,"to do saved succefully",Toast.LENGTH_SHORT).show()
               todoEt.text=null

           }else{
               Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
           }
           popupfrag!!.dismiss()
       }
    }

    override fun onupdateTask(todoData: tododata, todoEt: TextInputEditText) {
        val map=HashMap<String,Any>()
        map[todoData.taskId]=todoData.task
        databaseref.updateChildren(map).addOnCompleteListener {
            if(it.isSuccessful)
            {
                Toast.makeText(context,"Updated Susseccfylly",Toast.LENGTH_SHORT).show()
                todoEt.text=null
            }else{
                Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()

            }
            popupfrag!!.dismiss()
        }
    }

    override fun ondelete(tododata: tododata) {
        databaseref.child(tododata.taskId).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"deleted",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onedite(tododata: tododata) {
        if(popupfrag!=null){
            childFragmentManager.beginTransaction().remove(popupfrag!!).commit()

        }
        popupfrag=addtodoFragment.newInstance(tododata.taskId,tododata.task)
        popupfrag!!.setListener(this)
        popupfrag!!.show(
            childFragmentManager,
            addtodoFragment.TAG
        )
    }


}