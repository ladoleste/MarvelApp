package com.rviannaoliveira.marvelapp.detailCharacter.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.data.repository.KeyDatabase
import com.rviannaoliveira.marvelapp.detailCharacter.di.DetailCharacterModule
import com.rviannaoliveira.marvelapp.detailCharacter.ui.adapter.DetailComicsAdapter
import com.rviannaoliveira.marvelapp.detailCharacter.ui.adapter.DetailSeriesAdapter
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 09/04/17.
 */

class DetailCharacterFragment : Fragment(), DetailCharacterView, SupportFragmentInjector {
    override val injector: KodeinInjector = KodeinInjector()
    private val detailCharacterPresenterImpl by injector.instance<DetailCharacterPresenter>()
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var progressbar: ProgressBar
    private lateinit var appActivity: AppCompatActivity
    private lateinit var blockComics: LinearLayout
    private lateinit var blockSeries: LinearLayout
    private lateinit var reclycerViewComic: RecyclerView
    private lateinit var reclycerViewSeries: RecyclerView
    private lateinit var comicsAdapter: DetailComicsAdapter
    private lateinit var seriesAdapter: DetailSeriesAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initializeInjector()
        val view = inflater?.inflate(R.layout.fragment_character, container, false)

        appActivity = activity as AppCompatActivity
        appActivity.title = ""
        setHasOptionsMenu(true)

        image = view?.findViewById<ImageView>(R.id.image) as ImageView
        description = view.findViewById<TextView>(R.id.description) as TextView
        progressbar = view.findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
        blockComics = view.findViewById<LinearLayout>(R.id.block_comics) as LinearLayout
        blockSeries = view.findViewById<LinearLayout>(R.id.block_series) as LinearLayout
        reclycerViewComic = view.findViewById<RecyclerView>(R.id.list_comic) as RecyclerView
        reclycerViewSeries = view.findViewById<RecyclerView>(R.id.list_series) as RecyclerView

        loadView()
        detailCharacterPresenterImpl.getMarvelCharacter(arguments.get(KeyDatabase.ID) as Int)
        return view
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        activity.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun provideOverridingModule(): Kodein.Module = DetailCharacterModule(this).dependenciesKodein

    override fun loadView() {
        comicsAdapter = DetailComicsAdapter()
        seriesAdapter = DetailSeriesAdapter()

        reclycerViewComic.adapter = comicsAdapter
        reclycerViewComic.setHasFixedSize(true)
        reclycerViewComic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        reclycerViewSeries.adapter = seriesAdapter
        reclycerViewSeries.setHasFixedSize(true)
        reclycerViewSeries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun loadCharacter(marvelCharacter: MarvelCharacter) {
        MarvelUtil.setImageUrl(activity, marvelCharacter.thumbMail?.getPathExtension(), image)
        description.text = if (marvelCharacter.description?.length == 0) getString(R.string.no_description) else marvelCharacter.description
        appActivity.supportActionBar?.title = marvelCharacter.name

        marvelCharacter.comicList?.let {
            if (it.isEmpty()) {
                return
            }
            comicsAdapter.setComics(it)
            blockComics.visibility = View.VISIBLE
        }
        marvelCharacter.seriesList?.let {
            if (it.isEmpty()) {
                return
            }
            seriesAdapter.setSeries(it)
            blockSeries.visibility = View.VISIBLE
        }
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun error() {
        if (this.isVisible) {
            MarvelUtil.showErrorScreen(context, view, resources, R.drawable.wolverine_error)
        }
    }
}