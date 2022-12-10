package com.dev.miasnikoff.bookworm.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.LeafViewBinding

@RequiresApi(Build.VERSION_CODES.M)
class LeafView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.style.LeafViewStyle,
    defStyleAttr: Int = R.attr.leafViewStyle
) :
    CardView(context, attrs, androidx.cardview.R.attr.cardViewStyle) {

    private var counter: Int = 0
    private var maxValue: Int = 0
    private var viewColor: Int = 0
    private var iconsColor: Int = 0
    private var viewCornerRadius: Float = 0f

    private var binding: LeafViewBinding


    init {
        attrs?.let { initAttrs(it, defStyleAttr, defStyleRes) }
        binding = LeafViewBinding.inflate(LayoutInflater.from(context), this)
        setCardBackgroundColor(viewColor)
        setIconsColor(iconsColor)
        radius = viewCornerRadius
        binding.lvFirstPage.setOnClickListener { onFirstPageClick() }
        binding.lvLastPage.setOnClickListener { onLastPageClick() }
        binding.lvNextPage.setOnClickListener { onNextPageClick() }
        binding.lvPreviousPage.setOnClickListener { onPreviousPageClick() }
        binding.lvCounter.setText(counter.toString())
        binding.lvCounter.doOnTextChanged { text, _, _, _ ->
            text?.toString()?.toInt()?.let { newValue ->
                if (newValue != counter) {
                    counter = newValue.coerceIn(0, maxValue)
                }
            }
        }
    }

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LeafView, defStyleAttr, defStyleRes).apply {
            try {
                maxValue = getInteger(R.styleable.LeafView_leafMaxCount, 0)
                viewColor = getColor(R.styleable.LeafView_leafColor, 0)
                iconsColor = getColor(R.styleable.LeafView_leafIconsTint, 0)
                viewCornerRadius = getDimension(R.styleable.LeafView_leafCornerRadius, 0f)
            } finally {
                recycle()
            }
        }
    }

    private fun setIconsColor(colorRes: Int) {
        binding.lvPreviousPage.drawable.setTint(colorRes)
        binding.lvFirstPage.drawable.setTint(colorRes)
        binding.lvNextPage.drawable.setTint(colorRes)
        binding.lvLastPage.drawable.setTint(colorRes)
    }

    private fun onFirstPageClick() {
        unFocus()
        hideKeyboard()
        setCounter(0)
    }

    private fun onLastPageClick() {
        unFocus()
        hideKeyboard()
        setCounter(maxValue)
    }

    private fun onNextPageClick() {
        unFocus()
        hideKeyboard()
        setCounter(counter + 1)
    }

    private fun onPreviousPageClick() {
        unFocus()
        hideKeyboard()
        setCounter(counter - 1)
    }

    fun setCounter(value: Int) {
        counter = value.coerceIn(0, maxValue)
        binding.lvCounter.setText(counter.toString())
    }

    fun setMaxValue(value: Int) {
        maxValue = value
    }

    fun getCounter(): Int = counter

    private fun unFocus() {
        binding.root.findFocus()?.clearFocus()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

    private fun Float.toDp(): Float {
        return context.resources.displayMetrics.density * this
    }
}