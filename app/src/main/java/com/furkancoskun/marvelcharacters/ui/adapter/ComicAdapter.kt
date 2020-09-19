package com.furkancoskun.marvelcharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.furkancoskun.marvelcharacters.R
import com.furkancoskun.marvelcharacters.data.model.ComicItems
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ComicItems>() {

        override fun areItemsTheSame(
            oldItem: ComicItems,
            newItem: ComicItems
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ComicItems,
            newItem: ComicItems
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ComicViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_comic,
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
        list: ArrayList<ComicItems>
    ) {
        differ.submitList(list)
    }

    fun getSelectedItemId(position: Int): ComicItems {
        return differ.currentList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }

    }

    class ComicViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: ComicItems) =
            with(itemView) {
                // Set onClickListeners
                itemView.setOnClickListener {
                    interaction?.onComicSelected(
                        adapterPosition,
                        item
                    )
                }

                // Setup UI
                itemView.tv_comic.text = item.name

            }
    }

    // Interactions
    interface Interaction {
        fun onComicSelected(
            position: Int,
            item: ComicItems
        )
    }
}