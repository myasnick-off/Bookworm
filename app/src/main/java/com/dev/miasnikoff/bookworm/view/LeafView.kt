package com.dev.miasnikoff.bookworm.view

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.LeafViewBinding
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.lang.NumberFormatException
import kotlin.math.abs

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
    private var textColor: Int = 0
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
        binding.lvCounter.setTextColor(textColor)
        binding.lvCounter.doOnTextChanged { text, _, _, _ ->
            try {
                text?.toString()?.toInt()?.let { newValue ->
                    if (newValue != counter) {
                        counter = newValue.coerceIn(0, maxValue)
                    }
                }
            } catch (ex: NumberFormatException ) {
                Toast.makeText(
                    binding.lvCounter.context,
                    resources.getText(R.string.wrong_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        isSaveEnabled = true
        isSaveFromParentEnabled = true
    }

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LeafView, defStyleAttr, defStyleRes)
            .apply {
            try {
                maxValue = getInteger(R.styleable.LeafView_leafMaxCount, 0)
                viewColor = getColor(R.styleable.LeafView_leafColor, 0)
                iconsColor = getColor(R.styleable.LeafView_leafIconsTint, 0)
                textColor = getColor(R.styleable.LeafView_leafTextColor, 0)
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
        setCounter(ABSOLUT_MIN_VALUE)
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
        counter = value.coerceIn(ABSOLUT_MIN_VALUE, maxValue)
        binding.lvCounter.setText(counter.toString())
    }

    fun setMaxValue(value: Int) {
        maxValue =
            if (value > ABSOLUT_MAX_VALUE || value < ABSOLUT_MIN_VALUE) ABSOLUT_MAX_VALUE else value
    }

    fun getCounter(): Int = counter

    private fun unFocus() {
        binding.root.findFocus()?.clearFocus()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = super.onSaveInstanceState()
        return SavedState(counter, maxValue, state)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        state as SavedState
        super.onRestoreInstanceState(state.superState)
        setCounter(state.counter)
        setMaxValue(state.maxValue)
    }

    @Parcelize
    class SavedState(
        val counter: Int,
        val maxValue: Int,
        @IgnoredOnParcel val source: Parcelable? = null
    ) : BaseSavedState(source)

    companion object {
        const val ABSOLUT_MIN_VALUE = 0
        const val ABSOLUT_MAX_VALUE = 999
    }
}