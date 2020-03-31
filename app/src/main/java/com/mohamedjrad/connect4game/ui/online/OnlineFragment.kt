package com.mohamedjrad.connect4game.ui.online

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mohamedjrad.connect4game.R

class OnlineFragment : Fragment() {

    companion object {
        fun newInstance() = OnlineFragment()
    }

    private lateinit var viewModel: OnlineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.online_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OnlineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
