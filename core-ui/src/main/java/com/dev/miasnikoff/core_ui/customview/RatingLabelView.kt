package com.dev.miasnikoff.core_ui.customview

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.dev.miasnikoff.core_ui.R
import com.dev.miasnikoff.core_ui.databinding.RatingLabelViewBinding
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

class RatingLabelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = R.style.RatingLabelViewStyle,
    defStyleAttr: Int = R.attr.ratingLabelViewStyle
) : CardView(context, attrs, androidx.cardview.R.attr.cardViewStyle) {

    private var value: Float = 0.0f
    private var maxValue: Float = 5.0f
    private var viewColor: Int = 0
    private var iconColor: Int = 0
    private var textColor: Int = 0
    private var viewCornerRadius: Float = 0f

    private var binding: RatingLabelViewBinding

    init {
        attrs?.let { initAttrs(it, defStyleAttr, defStyleRes) }
        binding = RatingLabelViewBinding.inflate(LayoutInflater.from(context), this)
        setCardBackgroundColor(viewColor)
        setIconColor(iconColor)
        setTextColor(textColor)
        setValue(value)
        radius = viewCornerRadius
        isSaveEnabled = true
        isSaveFromParentEnabled = true
    }

    fun setValue(value: Float) {
        this.value = value.coerceIn(ABSOLUT_MIN_VALUE, maxValue)
        binding.ratingValue.text = String.format("%.01f", this.value)
    }

    fun getValue(): Float = this.value

    fun setMaxValue(value: Float) {
        maxValue = value.coerceIn(ABSOLUT_MIN_VALUE, ABSOLUT_MAX_VALUE)
    }

    fun setIconColor(colorRes: Int) {
        binding.ratingIcon.drawable.setTint(colorRes)
    }

    fun setTextColor(colorRes: Int) {
        binding.ratingValue.setTextColor(colorRes)
    }

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.RatingLabelView, defStyleAttr, defStyleRes)
            .apply {
                try {
                    maxValue = getFloat(R.styleable.RatingLabelView_ratingMaxValue, 0f)
                    viewColor = getColor(R.styleable.RatingLabelView_ratingLabelColor, 0)
                    iconColor = getColor(R.styleable.RatingLabelView_ratingLabelIconTint, 0)
                    textColor = getColor(R.styleable.RatingLabelView_ratingLabelTextColor, 0)
                    viewCornerRadius = getDimension(R.styleable.RatingLabelView_ratingLabelCornerRadius, 0f)
                } finally {
                    recycle()
                }
            }
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = super.onSaveInstanceState()
        return SavedState(value, maxValue, state)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        state as SavedState
        super.onRestoreInstanceState(state.superState)
        setValue(state.value)
        setMaxValue(state.maxValue)
    }

    @Parcelize
    class SavedState(
        val value: Float,
        val maxValue: Float,
        @IgnoredOnParcel val source: Parcelable? = null
    ) : BaseSavedState(source)

    companion object {
        const val ABSOLUT_MIN_VALUE = 0f
        const val ABSOLUT_MAX_VALUE = 9.9f
    }
}