package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.DialogAddBookBinding
import com.example.libraryapp.databinding.FragmentBookingListBinding
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.viewmodel.BookListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BookingListFragment : Fragment(R.layout.fragment_booking_list) {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = binding.addButton
        setupRecyclerView()


        button.setOnClickListener {
            showAddBookDialog()
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            bookAdapter.submitList(books)
            binding.emptyView.isVisible = books.isEmpty()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(

            onItemClick = { book ->
                val bundle = Bundle()
                bundle.putInt("book_id", book.id)
                view?.findNavController()?.navigate(R.id.action_bookingListFragment_to_bookingDetailFragment, bundle)
            },
            onDeleteBook = { bookId ->
                onDeleteBook(bookId) // Llama a la función onDeleteBook del Fragment
            }
        )
        binding.recyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }


    private fun showAddBookDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Book")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                with(dialogBinding) {
                    viewModel.addBook(dialogBinding.titleInput.text.toString(),dialogBinding.authorInput.text.toString(),
                        dialogBinding.yearInput.text.toString(),dialogBinding.descriptionInput.text.toString(), dialogBinding.availableCheckBox.isChecked)
                    Log.d("AddBookDialog", "Add button clicked")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun onDeleteBook(bookId: Int) {
        viewModel.deleteBook(bookId)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}