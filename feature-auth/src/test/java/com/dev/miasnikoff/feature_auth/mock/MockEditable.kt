package com.dev.miasnikoff.feature_auth.mock

import android.text.Editable
import android.text.InputFilter

class MockEditable(private val str: String) : Editable {

    override fun get(index: Int): Char = str[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        str.subSequence(startIndex, endIndex)

    override fun getChars(start: Int, end: Int, dest: CharArray?, destoff: Int) {}

    override fun <T : Any?> getSpans(start: Int, end: Int, type: Class<T>?): Array<T>? = null

    override fun getSpanStart(tag: Any?): Int = 0

    override fun getSpanEnd(tag: Any?): Int = 0

    override fun getSpanFlags(tag: Any?): Int = 0

    override fun nextSpanTransition(start: Int, limit: Int, type: Class<*>?): Int = 0

    override fun setSpan(what: Any?, start: Int, end: Int, flags: Int) {}

    override fun removeSpan(what: Any?) {}

    override fun append(text: CharSequence?): Editable = this

    override fun append(text: CharSequence?, start: Int, end: Int): Editable = this

    override fun append(text: Char): Editable = this

    override fun replace(st: Int, en: Int, source: CharSequence?, start: Int, end: Int): Editable =
        this

    override fun replace(st: Int, en: Int, text: CharSequence?): Editable = this

    override fun insert(where: Int, text: CharSequence?, start: Int, end: Int): Editable = this

    override fun insert(where: Int, text: CharSequence?): Editable = this

    override fun delete(st: Int, en: Int): Editable = this

    override fun clear() {}

    override fun clearSpans() {}

    override fun setFilters(filters: Array<out InputFilter>?) {}

    override fun getFilters(): Array<InputFilter> = arrayOf()

    override val length: Int
        get() = str.length

}