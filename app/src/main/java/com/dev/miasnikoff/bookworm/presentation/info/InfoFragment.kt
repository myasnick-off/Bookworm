package com.dev.miasnikoff.bookworm.presentation.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentInfoBinding
import com.dev.miasnikoff.bookworm.presentation._core.BaseFragment
import com.dev.miasnikoff.bookworm.presentation._core.model.UserModel
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment : BaseFragment() {

    private lateinit var _binding: FragmentInfoBinding
    override val binding: FragmentInfoBinding
        get() = _binding

    private val user: UserModel
        get() = arguments?.getParcelable(ARG_USER_DATA) ?: throw IllegalStateException("No data!")

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
    }

    override fun initView() = with(binding) {
        nameText.text = user.name
        berthText.text = user.berthDate?.let { dateFormat.format(it) }
        addressText.text = user.address
        emailText.text = user.email
        sendButton.setOnClickListener {
            parentFragmentManager.popBackStack()
            sendData()
        }
    }

    private fun sendData() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_TEXT, user.toString())
        }
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.send_message)))
        } catch (ex: ActivityNotFoundException) {
            Log.e(LOG_TAG, ex.message ?: ERROR_MESSAGE)
            Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARG_USER_DATA = "arg_user_data"
        private const val LOG_TAG = "InfoFragment"
        private const val ERROR_MESSAGE = "Failed to send e-mail!"

        fun newInstance(user: UserModel): InfoFragment = InfoFragment().apply {
            arguments = bundleOf(ARG_USER_DATA to user)
        }
    }
}