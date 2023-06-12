package com.example.todo.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.google.firebase.auth.FirebaseAuth


class splashFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var navcontroller:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        Handler(Looper.myLooper()!!).postDelayed(Runnable {

                if (auth.currentUser != null) {
                    navcontroller.navigate(R.id.action_splashFragment_to_homeFragment2)
                } else {
                    navcontroller.navigate(R.id.action_splashFragment_to_signinFragment)
                }

        },2000)
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        navcontroller = Navigation.findNavController(view)
    }
}