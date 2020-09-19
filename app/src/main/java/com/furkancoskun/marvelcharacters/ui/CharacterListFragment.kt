package com.furkancoskun.marvelcharacters.ui

import com.furkancoskun.marvelcharacters.ui.adapter.CharacterAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkancoskun.marvelcharacters.R
import com.furkancoskun.marvelcharacters.data.model.CharacterResult
import com.furkancoskun.marvelcharacters.ui.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_character_list.*
import com.furkancoskun.marvelcharacters.utils.Status
import com.google.gson.Gson

class CharacterListFragment : Fragment(), CharacterAdapter.Interaction {

    // Variables
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init character view model
        characterViewModel = activity?.run {
            ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        // Inits
        initRecyclerView()
        getAllCharacter()
    }

    // Network calls
    private fun getAllCharacter() {

        characterViewModel.getAllCharacter(
            "test",
            "4cc13c4fb0b4e6851536de41016a6b63",
            "89b19bfccfae010dc9e77677c59b35cd",
            "30"
        ).observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    pb_loading.visibility = View.GONE

                    resource.data?.let {
                        characterAdapter.submitList(it.data.results)
                    }
                }
                Status.ERROR -> {
                    pb_loading.visibility = View.GONE
                }
                Status.EMPTY -> {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

    private fun initRecyclerView() {
        list_character.apply {
            layoutManager = LinearLayoutManager(context)

            characterAdapter =
                CharacterAdapter(
                    interaction = this@CharacterListFragment
                )

            adapter = characterAdapter
        }
    }

    override fun onCharacterSelected(position: Int, item: CharacterResult) {

        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                Gson().toJson(item)
            )
        )
    }

}

