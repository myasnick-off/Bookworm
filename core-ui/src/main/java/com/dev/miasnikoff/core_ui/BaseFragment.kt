package com.dev.miasnikoff.core_ui

import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    abstract val binding: ViewBinding
    abstract val viewModel: BaseViewModel

    open val titleRes: Int? = null
    open val isStickyToolbar: Boolean = false
    open val hasBackButton: Boolean = true
    open val hasShareButton: Boolean = false
    var hasFavoriteButton: Boolean = false
    var hasRemoveButton: Boolean = false
    open val hasRemoveAllButton: Boolean = false
    open val shareAction: () -> Unit = {}
    open val removeAllAction: () -> Unit = {}

    val toolbar: Toolbar?
        get() = view?.findViewById(R.id.toolbar)

    val collapsingToolbar: CollapsingToolbarLayout?
        get() = view?.findViewById(R.id.collapsing_toolbar)

    open fun initMenu() {
        collapsingToolbarConfig()
        toolbarConfig()
    }

    open fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.back()
        }
    }

    private fun collapsingToolbarConfig() {
        collapsingToolbar?.apply {
            if (isStickyToolbar) {
                (layoutParams as AppBarLayout.LayoutParams)
                    .scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            }
        }
    }

    private fun toolbarConfig() {
        toolbar?.apply {
            titleRes?.let { title = getText(it) }
            upIconConfig()
            menuButtonsConfig()
            setOnMenuItemClickListener {menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_remove_all -> {
                        removeAllAction()
                        true
                    }
                    R.id.menu_remove_selected -> {
                        //viewModel.removeSelected()
                        true
                    }
                    R.id.menu_share -> {
                        shareAction()
                        true
                    }
                    R.id.menu_favorite -> {
                        //viewModel.addToFavorite()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun Toolbar.menuButtonsConfig() {
        overflowIcon = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_overflow_24, context.theme)
        menu?.apply {
            findItem(R.id.menu_favorite).isVisible = hasFavoriteButton
            findItem(R.id.menu_share).isVisible = hasShareButton
            findItem(R.id.menu_remove_selected).isVisible = hasRemoveButton
            findItem(R.id.menu_remove_all).isVisible = hasRemoveAllButton
        }
    }

    private fun Toolbar.upIconConfig() {
        if (hasBackButton) {
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { viewModel.back() }
        }
    }
}