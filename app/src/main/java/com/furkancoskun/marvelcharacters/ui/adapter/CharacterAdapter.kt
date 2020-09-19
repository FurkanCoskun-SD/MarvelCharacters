package com.furkancoskun.marvelcharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancoskun.marvelcharacters.R
import com.furkancoskun.marvelcharacters.data.model.CharacterResult
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterResult>() {

        override fun areItemsTheSame(
            oldItem: CharacterResult,
            newItem: CharacterResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterResult,
            newItem: CharacterResult
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_character,
                viewGroup,
                false
            ),
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(
        list: ArrayList<CharacterResult>
    ) {
        differ.submitList(list)
    }

    fun getSelectedItemId(position: Int): CharacterResult {
        return differ.currentList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }

    }

    class CharacterViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: CharacterResult) =
            with(itemView) {
                // Set onClickListeners
                itemView.setOnClickListener {
                    interaction?.onCharacterSelected(
                        adapterPosition,
                        item
                    )
                }

                // Setup UI
                itemView.tv_name.text = item.name

                // Load image
                Glide.with(context).load(item.thumbnail.path + "." + item.thumbnail.extension).into(itemView.iv_image)
            }
    }

    // Interactions
    interface Interaction {
        fun onCharacterSelected(
            position: Int,
            item: CharacterResult
        )
    }
}