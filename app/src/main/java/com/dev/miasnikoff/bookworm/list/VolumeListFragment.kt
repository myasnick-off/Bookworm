package com.dev.miasnikoff.bookworm.list

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.databinding.FragmentListBinding
import com.dev.miasnikoff.bookworm.list.adapter.VolumeListAdapter
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar

class VolumeListFragment : BaseFragment() {

    private lateinit var _binding: FragmentListBinding
    override val binding: FragmentListBinding
        get() = _binding

    private val viewModel: VolumeListViewModel by lazy {
        ViewModelProvider(this)[VolumeListViewModel::class.java]
    }

    private val itemClickListener = object : VolumeListAdapter.ItemClickListener {
        override fun onItemClick(itemId: String) {
            Toast.makeText(context, "Made click on item with id #$itemId", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onItemLongClick(itemId: String): Boolean {
            Toast.makeText(context, "Made long click on item with id #$itemId", Toast.LENGTH_SHORT)
                .show()
            return true
        }

        override fun onIconClick(itemId: String) {
            viewModel.setFavorite(itemId)
        }
    }

    private val pageListener = object : VolumeListAdapter.PageListener {
        override fun loadNextPage() {
            viewModel.loadNextPage(DEFAULT_QUERY)
        }
    }

    private val volumeListAdapter: VolumeListAdapter =
        VolumeListAdapter(itemClickListener, pageListener)

    private val fabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.fab_appearance_anim).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
        initPresenter()
    }

    override fun initView() {
        binding.volumeList.adapter = volumeListAdapter
        binding.listFab?.startAnimation(fabAnim)
        binding.listFab?.visibility = View.VISIBLE
        /*val animScaleX = ObjectAnimator.ofFloat(binding.listFab, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        val animScaleY = ObjectAnimator.ofFloat(binding.listFab, View.SCALE_Y, 0f, 1f).apply {
            duration = 1500

            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        val animAlpha = ValueAnimator.ofFloat( 0f, 1f).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        val animSet = AnimatorSet().apply {
            playTogether(animScaleX, animScaleY, animAlpha)
            start()
        }*/
    }

    private fun initPresenter() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderState)
        getData()
    }

    private fun renderState(state: VolumeListState) {
        when (state) {
            is VolumeListState.Loading -> showLoading()
            is VolumeListState.MoreLoading -> showMoreLoading()
            is VolumeListState.Failure -> showError(state.message)
            is VolumeListState.Success -> showData(state.data, state.loadMore)
        }
    }

    private fun showLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
    }

    private fun showMoreLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.volumeList.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
    }

    private fun showData(volumes: List<RecyclerItem>, loadMore: Boolean) {
        binding.listLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.VISIBLE
        volumeListAdapter.submitList(volumes)
        volumeListAdapter.loadMore = loadMore
    }

    private fun showError(message: String) {
        binding.listLoader.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { getData() }
    }

    private fun getData(query: String = DEFAULT_QUERY) {
        viewModel.getInitialPage(query)
    }

    companion object {
        private const val DEFAULT_QUERY = "The"
        fun newInstance(): VolumeListFragment = VolumeListFragment()
    }
}