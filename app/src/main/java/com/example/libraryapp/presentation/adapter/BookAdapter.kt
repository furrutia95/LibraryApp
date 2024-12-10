package com.example.libraryapp.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.databinding.ItemBookBinding
import com.example.libraryapp.domain.model.Book

class BookAdapter(
    private val onItemClick: (Book) -> Unit,
    private val onDeleteBook: (Int) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.deleteButton.setOnClickListener {
                val book = getItem(adapterPosition)
                if (book != null) {
                    onDeleteBook(book.id) // Llama a onDeleteBook con el ID del libro
                }
            }

        }

        fun bind(book: Book) {
            binding.apply {
                titleText.text = book.title
                authorText.text = book.author
                yearText.text = book.year.toString()

                availabilityChip.apply {
                    text = if (book.isAvailable) "Available" else "Checked Out"
                    chipBackgroundColor = ColorStateList.valueOf(
                        context.getColor(
                            if (book.isAvailable) R.color.available
                            else R.color.checked_out
                        )
                    )
                }
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
