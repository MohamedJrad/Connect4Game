package com.mohamedjrad.connect4game.ui.gameplay

import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mohamedjrad.connect4game.R
import com.mohamedjrad.connect4game.data.model.Cercle
import kotlinx.android.synthetic.main.grid_layout_item.view.*

@BindingAdapter("color")
fun ImageView.setImageResource(item: Cercle?) {
    item?.let {
        if (item.color.equals("RED")) {
            imageView.setImageResource(R.drawable.red)
            imageView.animation =
                AnimationUtils.loadAnimation(context, R.anim.slide_down)

        } else if (item.color.equals("YELLOW")) {
            imageView.setImageResource(R.drawable.yellow)
            imageView.animation =
                AnimationUtils.loadAnimation(context, R.anim.slide_down)
        } else if (item.color.equals("PURPLE")) {
            imageView.setImageResource(R.drawable.purple)
        } else
            imageView.setImageResource(R.drawable.green)

    }

}