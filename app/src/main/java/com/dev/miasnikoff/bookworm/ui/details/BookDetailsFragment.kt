package com.dev.miasnikoff.bookworm.ui.details

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentVolumeDetailsBinding
import com.dev.miasnikoff.bookworm.domain.model.BookDetails
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui.details.model.DetailsState
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import javax.inject.Inject

class BookDetailsFragment : BaseFragment() {

    private lateinit var _binding: FragmentVolumeDetailsBinding
    override val binding: FragmentVolumeDetailsBinding
        get() = _binding

    private val bookId: String by lazy {
        arguments?.getString(ARG_VOLUME_ID) ?: throw IllegalStateException()
    }

    @Inject
    lateinit var viewModelFactory: BookDetailsViewModelAssistedFactory
    private val viewModel: BookDetailsViewModel by viewModels { viewModelFactory.create(bookId) }

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolumeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() {
       // nothing to do
    }

    override fun initMenu() {
        super.initMenu()
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.detailsToolbar)
            title = getString(R.string.about_volume)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
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

    companion object {
        private const val ARG_VOLUME_ID = "arg_volume_id"

        fun newInstance(volumeId: String): BookDetailsFragment =
            BookDetailsFragment().apply {
                arguments = bundleOf(ARG_VOLUME_ID to volumeId)
            }
    }
}