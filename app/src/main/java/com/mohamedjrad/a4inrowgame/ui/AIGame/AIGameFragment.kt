package com.mohamedjrad.a4inrowgame.ui.AIGame

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mohamedjrad.a4inrowgame.R

class AIGameFragment : Fragment() {

    companion object {
        fun newInstance() = AIGameFragment()
    }

    private lateinit var viewModel: AIGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ai_game_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AIGameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
