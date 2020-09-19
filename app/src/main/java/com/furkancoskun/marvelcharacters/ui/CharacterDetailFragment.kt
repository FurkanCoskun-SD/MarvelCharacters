package com.furkancoskun.marvelcharacters.ui

import com.furkancoskun.marvelcharacters.ui.adapter.ComicAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.furkancoskun.marvelcharacters.R
import com.furkancoskun.marvelcharacters.data.model.CharacterResult
import com.furkancoskun.marvelcharacters.data.model.ComicItems
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_character_detail.*

class CharacterDetailFragment : Fragment(), ComicAdapter.Interaction {

    private lateinit var characterResultString: String
    private lateinit var characterResult: CharacterResult
    private lateinit var comicAdapter: ComicAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inits
        initRecyclerView()

        // Set verificationId
        arguments?.let {
            characterResultString = CharacterDetailFragmentArgs.fromBundle(it).characterResult
            characterResult = Gson().fromJson(characterResultString, CharacterResult::class.java)

            Glide.with(this).load(characterResult.thumbnail.path + "." + characterResult.thumbnail.extension).into(iv_image)
            tv_name.text = characterResult.name

            if (characterResult.description != "") {
                tv_desc.text = characterResult.description
            } else {
                tv_desc.text = getString(R.string.DescNotFound)
            }

            characterResult.comics?.let {
                comicAdapter.submitList(it.items)
            }


        }
    }

    private fun initRecyclerView() {
        list_comic.apply {
            layoutManager = LinearLayoutManager(context)

            comicAdapter =
                ComicAdapter(
                    interaction = this@CharacterDetailFragment
                )

            adapter = comicAdapter
        }
    }

    override fun onComicSelected(position: Int, item: ComicItems) {
    }

}