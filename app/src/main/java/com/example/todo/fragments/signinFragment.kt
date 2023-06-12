package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth

class signinFragment : Fragment() {


    private lateinit var auth:FirebaseAuth
    private lateinit var navcontrol:NavController
    private lateinit var binding:FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSigninBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerevent()
    }
    private fun init(view :View){
        navcontrol= Navigation.findNavController(view)

        auth=FirebaseAuth.getInstance()
    }
    private fun registerevent(){

        binding.textViewSignUp.setOnClickListener {
            navcontrol.navigate(R.id.action_signinFragment_to_signupFragment2)
        }


        binding.nextBtn.setOnClickListener {
            val email=binding.emailEt.text.toString().trim()
            val pass=binding.passEt.text.toString().trim()

            if(!email.isEmpty()&&!pass.isEmpty()){

                //progress bar
                binding.progressBar.visibility=View.VISIBLE

                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){

                            Toast.makeText(context,"login successfully", Toast.LENGTH_SHORT).show()
                            navcontrol.navigate(R.id.action_signinFragment_to_homeFragment2)

                        }else{
                            Toast.makeText(context,it.exception?.message, Toast.LENGTH_SHORT).show()

                        }
                        binding.progressBar.visibility=View.GONE
                    }

            }
            else{
                Toast.makeText(context,"empty fields not allowed",Toast.LENGTH_SHORT).show()
            }
        }
    }


}