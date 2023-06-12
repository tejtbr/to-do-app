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
import com.example.todo.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class signupFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var navcontrol:NavController
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerevent()
    }
    private fun init(view :View){
        navcontrol=Navigation.findNavController(view)

        auth=FirebaseAuth.getInstance()
    }
    private fun registerevent(){

        binding.textViewSignIn.setOnClickListener {
            navcontrol.navigate(R.id.action_signupFragment_to_signinFragment)
        }


        binding.nextBtn.setOnClickListener {
            val email=binding.emailEt.text.toString().trim()
            val pass=binding.passEt.text.toString().trim()
            val verify=binding.verifyPassEt.text.toString().trim()

            if(!email.isEmpty()&&!pass.isEmpty()&&!verify.isEmpty()){
                if(pass==verify){
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(context,"registerd successfully",Toast.LENGTH_SHORT).show()
                            navcontrol.navigate(R.id.action_signupFragment_to_homeFragment2)

                        }else{
                            Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }



}


