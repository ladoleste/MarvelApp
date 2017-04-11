package com.rviannaoliveira.marvelapp.util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rviannaoliveira.marvelapp.R

/**
 * Criado por rodrigo on 09/04/17.
 */
object MarvelUtil {

    fun setImageUrl(context: Context, url: String, image: ImageView, width: Int, height: Int) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .error(R.drawable.image_broken)
                .placeholder(R.drawable.ic_marvel_comics)
                .into(image)
    }

    fun showFragment(context: AppCompatActivity, id: Int, fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager = context.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(id, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

}