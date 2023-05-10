package com.example.quizapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setImage")
fun ImageView.setImage(status: Int){
    if(status == 0)
    {
        this.setImageResource(R.drawable.baseline_bookmark_border)
    }else{
        this.setImageResource(R.drawable.baseline_bookmark_added_)
    }
}

