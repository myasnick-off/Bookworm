package com.dev.miasnikoff.feature_tabs.ui.details

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentBookDetailsBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.BookDetailsAdapter
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsCell
import com.dev.miasnikoff.feature_tabs.ui.details.model.DetailsState
import javax.inject.Inject

class BookDetailsFragment : BaseFragment(R.layout.fragment_book_details) {

    override lateinit var binding: FragmentBookDetailsBinding
    override val titleRes: Int = R.string.about_volume
    override val hasShareButton: Boolean = true
    override val shareAction: () -> Unit = { shareLink() }

    private val args: BookDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: BookDetailsViewModelAssistedFactory
    override val viewModel: BookDetailsViewModel by viewModels { viewModelFactory.create(args.bookId) }

    private val controlsClickListener = object : BookControlsCell.ControlsClickListener {
        override fun onFavoriteClick() {
            viewModel.setFavorite()
        }

        override fun onPreviewClick(url: String) {
            openBrowser(url)
        }
    }

    private val detailsAdapter = BookDetailsAdapter(controlsClickListener)

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
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
        super.initView()
        binding.detailsRecycler.adapter = detailsAdapter
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
    }

    private fun renderData(state: DetailsState) {
        when (state) {
            DetailsState.Loading -> showLoading()
            is DetailsState.Failure -> showError(state.message)
            is DetailsState.Success -> showData(state.data)
        }
    }

    private fun showLoading() {
        binding.detailsLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.detailsRecycler.visibility = View.GONE
    }

    private fun showData(bookDetails: List<RecyclerItem>) {
        binding.detailsLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.detailsRecycler.visibility = View.VISIBLE
        detailsAdapter.submitList(bookDetails)
    }

    private fun showError(message: String) {
        binding.detailsLoader.visibility = View.GONE
        binding.detailsRecycler.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(com.dev.miasnikoff.core_ui.R.string.error)} $message",
            actionText = getString(com.dev.miasnikoff.core_ui.R.string.reload)
        ) { viewModel.getDetails() }
    }

    private fun openBrowser(contentUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(contentUrl)
        }
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.open_with)))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), getString(R.string.browser_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun shareLink() {
        viewModel.getBookUrl()?.let { bookUrl ->
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_TEXT, bookUrl)
            }
            try {
                startActivity(Intent.createChooser(intent, getString(R.string.share_link)))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(requireContext(), getString(R.string.messenger_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}