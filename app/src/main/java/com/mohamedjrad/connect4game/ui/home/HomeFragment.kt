package com.mohamedjrad.connect4game.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mohamedjrad.connect4game.R
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {





    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        button.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_gamePlayFragment)
        }
    }


}
