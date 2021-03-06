package com.rviannaoliveira.marvelapp.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseActivity
import com.rviannaoliveira.marvelapp.characters.ui.CharactersFragment
import com.rviannaoliveira.marvelapp.comics.ui.ComicsFragment
import com.rviannaoliveira.marvelapp.favorite.FavoriteFragment
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class MainActivity : BaseActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem -> onNavigationItemReselectedListener(menuItem) }

        if (savedInstanceState == null) {
            MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false, MarvelConstant.CHARACTERS)
        }
    }

    private fun onNavigationItemReselectedListener(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_character -> MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false, MarvelConstant.CHARACTERS)
            R.id.action_comic -> MarvelUtil.showFragment(this, R.id.content_main, ComicsFragment(), false, MarvelConstant.COMICS)
            R.id.action_favorite -> MarvelUtil.showFragment(this, R.id.content_main, FavoriteFragment(), false, MarvelConstant.FAVORITES)
        }
        return true
    }
}
