package com.dev.miasnikoff.bookworm.presentation.details

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.presentation._core.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentVolumeDetailsBinding
import com.dev.miasnikoff.bookworm.presentation.details.model.DetailsState
import com.dev.miasnikoff.bookworm.presentation.details.model.Volume
import com.dev.miasnikoff.bookworm.presentation.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar

class VolumeDetailsFragment : BaseFragment() {

    private lateinit var _binding: FragmentVolumeDetailsBinding
    override val binding: FragmentVolumeDetailsBinding
        get() = _binding

    private val viewModel: VolumeDetailsViewModel by lazy {
        ViewModelProvider(this)[VolumeDetailsViewModel::class.java]
    }

    private val volumeId: String by lazy {
        arguments?.getString(ARG_VOLUME_ID) ?: throw IllegalStateException()
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
        viewModel.getDetails(volumeId)
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

    private fun showData(volume: Volume) {
        binding.detailsLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.detailsLayout.visibility = View.VISIBLE
        bindData(volume)
    }

    private fun showError(message: String) {
        binding.detailsLoader.visibility = View.GONE
        binding.detailsLayout.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { viewModel.getDetails(volumeId) }
    }

    private fun bindData(volume: Volume) = with(binding) {
        ratingBar.rating = volume.averageRating
        bookRating.text = String.format("%.1f", volume.averageRating)
        bookTitle.text = volume.title
        bookSubtitle.text = volume.subtitle
        authors.text = volume.authors
        categories.text = volume.categories
        publisher.text = volume.publisher
        publishedDate.text = volume.publishedDate
        language.text = volume.language
        bookDetails.text = volume.description
        Glide.with(root.context)
            .load(volume.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(bookImage)
    }

    companion object {
        private const val ARG_VOLUME_ID = "arg_volume_id"

        fun newInstance(volumeId: String): VolumeDetailsFragment =
            VolumeDetailsFragment().apply {
                arguments = bundleOf(ARG_VOLUME_ID to volumeId)
            }
    }
}