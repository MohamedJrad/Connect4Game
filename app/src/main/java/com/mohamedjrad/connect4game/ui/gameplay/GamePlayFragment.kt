package com.mohamedjrad.connect4game.ui.gameplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedjrad.connect4game.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.game_play_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class GamePlayFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GamePlayViewModel::class.java]
    }

    private lateinit var navController: NavController
    private var columnCount = 7
    private lateinit var adapter: GridAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_play_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        resultTextView.visibility = View.INVISIBLE

        viewModel.gameLogic.redPlayerScoreLive.observe(viewLifecycleOwner, Observer {
            redPlayerTextView.text = it.toString()
        })
        viewModel.gameLogic.yellowPlayerScoreLive.observe(viewLifecycleOwner, Observer {
            yellowPlayerTextView.text = it.toString()
        })
        viewModel.gameLogic.roundNum.observe(viewLifecycleOwner, Observer {
            roundCountertextView.text = it.toString() + "/5"
        })

        next_round_button.setOnClickListener {
            viewModel.gameLogic.roundState = true
            viewModel.gameLogic.roundStart()
            next_round_button.visibility = View.INVISIBLE
            resultTextView.visibility = View.INVISIBLE
        }
        navController = Navigation.findNavController(view)
        playAgainButton.setOnClickListener {


            navController.navigate(R.id.action_gamePlayFragment_self)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GlobalScope.launch {
            if (viewModel.gameLogic.getCercles().size != 42) {
                viewModel.gameLogic.resetDatabase()
            }
        }
    }

    private fun setupRecyclerView() {

        adapter = GridAdapter(Listener {

            if (viewModel.gameLogic.roundState)
                viewModel.cercleClicked(it)



            if (!viewModel.gameLogic.roundState && viewModel.gameLogic.gameState
            ) {
                resultTextView.visibility = View.VISIBLE
                when (viewModel.gameLogic.roundWinner) {

                    0 -> resultTextView.text = "Its a Draw"
                    1 -> resultTextView.text =
                        "Red Win This Round by 4InRow ${viewModel.gameLogic.winBy4InRow}"

                    2 -> resultTextView.text =
                        "Yellow Win This Round by 4InRow ${viewModel.gameLogic.winBy4InRow}"
                }


                next_round_button.visibility = View.VISIBLE
            }


            if (!viewModel.gameLogic.gameState) {
                resultTextView.visibility = View.VISIBLE

                when (viewModel.gameLogic.gameWinner) {

                    0 -> resultTextView.text =
                        "its a draw"

                    1 -> resultTextView.text =
                        "Red Win This Game by win ${viewModel.gameLogic.redPlayerScoreLive.value.toString()} times"

                    2 -> resultTextView.text =
                        "Yellow Win This Game by win ${viewModel.gameLogic.yellowPlayerScoreLive.value.toString()} times"


                }
                playAgainButton.visibility = View.VISIBLE

            }

        })



        recycler_view.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }



        recycler_view.adapter = adapter
        viewModel.gameLogic.getCerclesLive().observe(viewLifecycleOwner, Observer
        {
            adapter.submitList(it)

        })


    }
}
