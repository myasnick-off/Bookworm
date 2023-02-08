package com.dev.miasnikoff.bookworm.ui.details

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentBookDetailsBinding
import com.dev.miasnikoff.bookworm.domain.model.BookDetails
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui.details.model.DetailsState
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import javax.inject.Inject

class BookDetailsFragment : BaseFragment(R.layout.fragment_book_details), MenuProvider {

    override lateinit var binding: FragmentBookDetailsBinding

    private val args: BookDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: BookDetailsViewModelAssistedFactory
    private val viewModel: BookDetailsViewModel by viewModels { viewModelFactory.create(args.bookId) }

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookDetailsBinding.bind(view)
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.back()
        }
    }

    override fun initMenu() {
        super.initMenu()
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.detailsToolbar)
            title = getString(R.string.about_volume)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            android.R.id.home -> {
                viewModel.back()
                true
            }
            else -> false
        }
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
    }

    private fun renderData(state: DetailsState) {
        when(state) {
            DetailsState.Loading -> showLoading()
            is DetailsState.Failure -> showError(state.message)
            is DetailsState.Success -> showData(state.data)
        }
    }

    private fun showLoading() {
        binding.detailsLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.detailsLayout.visibility = View.GONE
    }

    private fun showData(bookDetails: BookDetails) {
        binding.detailsLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.detailsLayout.visibility = View.VISIBLE
        bindData(bookDetails)
    }

    private fun showError(message: String) {
        binding.detailsLoader.visibility = View.GONE
        binding.detailsLayout.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { viewModel.getDetails() }
    }

    private fun bindData(bookDetails: BookDetails) = with(binding) {
        ratingBar.rating = bookDetails.averageRating
        bookRating.text = String.format("%.1f", bookDetails.averageRating)
        bookTitle.text = bookDetails.title
        bookSubtitle.text = bookDetails.subtitle
        authors.text = bookDetails.authors
        categories.text = bookDetails.categories
        publisher.text = bookDetails.publisher
        publishedDate.text = bookDetails.publishedDate
        language.text = bookDetails.language
        this.bookDetails.text = bookDetails.description
        Glide.with(root.context)
            .load(bookDetails.imageLinkSmall)
            .error(R.drawable.ic_broken_image_48)
            .into(bookImage)
    }
}