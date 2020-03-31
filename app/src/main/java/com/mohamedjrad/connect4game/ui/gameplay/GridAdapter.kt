package com.mohamedjrad.connect4game.ui.gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamedjrad.connect4game.data.model.Cercle
import com.mohamedjrad.connect4game.databinding.GridLayoutItemBinding

class GridAdapter(val clickListener: Listener) :
    ListAdapter<Cercle,GridAdapter.NoteViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)


    }


    class NoteViewHolder private constructor(val binding: GridLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cercle: Cercle,
            clickListener: Listener
        ) {
            binding.cercle=cercle
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }


        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridLayoutItemBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }


    }


}

class DiffCallback : DiffUtil.ItemCallback<Cercle>() {
    override fun areItemsTheSame(oldItem: Cercle, newItem: Cercle): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cercle, newItem: Cercle): Boolean {
        return oldItem == newItem
    }
}

class Listener(val clickListener: (cercleId: String) -> Unit) {
    fun onClick(cercle: Cercle) = clickListener(cercle.id)
}

