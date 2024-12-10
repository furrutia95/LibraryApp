package com.example.libraryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentBookingDetailBinding
import com.example.libraryapp.presentation.viewmodel.BookDetailViewModel

private const val ARG_PARAM1 = "book_id"
class BookingDetailFragment : Fragment(R.layout.fragment_booking_detail) {
    private var param1: Int? = null
    private val viewModel: BookDetailViewModel by viewModels()
    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadBook(param1!!)
        viewModel.book.observe(viewLifecycleOwner) { book ->

            if(book != null) {
                binding.apply {
                    titleText.text = book.title
                    authorText.text = book.author
                    yearText.text = book.year.toString()
                    descriptionText.text = book.description
                    availabilityChip.text = if (book.isAvailable) "Available" else "Checked Out"
                }
            }

        }
    }

    companion object {
        fun newInstance() =
            BookingDetailFragment()
    }
}