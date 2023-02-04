package com.dev.miasnikoff.bookworm.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentLoginBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.ViewModelFactory
import com.dev.miasnikoff.bookworm.ui._core.model.UserModel
import com.dev.miasnikoff.bookworm.ui.info.InfoFragment
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private lateinit var _binding: FragmentLoginBinding
    override val binding: FragmentLoginBinding
        get() = _binding

    private var disposables = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun initView() = with(binding) {
        val loginObservable = loginEditText.textChanges()
        val passObservable = passwordEditText.textChanges()
        disposables.add(
            Observable.combineLatest(loginObservable, passObservable) { login, pass ->
                login.isNotEmpty() && pass.isNotEmpty()
            }
                .distinctUntilChanged()
                .subscribe { fieldsNotEmpty -> loginButton.isEnabled = fieldsNotEmpty }
        )
        observeEditTextFocus(loginEditText, loginInputLayout)
        observeEditTextFocus(passwordEditText, passwordInputLayout)

        loginButton.setOnClickListener {
            hideSoftKeyboard()
            val login = binding.loginEditText.text
            val pass = binding.passwordEditText.text
            viewModel.getUser(login, pass)
        }
    }

    private fun observeEditTextFocus(editText: EditText, textInputLayout: TextInputLayout) {
        disposables.add(editText.focusChanges()
            .skipInitialValue()
            .subscribe { isFocused ->
                if (!isFocused && editText.text.isNullOrBlank()) {
                    textInputLayout.error = getString(R.string.required_field)
                    textInputLayout.isErrorEnabled = true
                } else {
                    textInputLayout.isErrorEnabled = false
                    binding.errorMessage.visibility = View.GONE
                }
            })
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
    }

    private fun renderData(state: AuthState) {
        when (state) {
            is AuthState.AuthFailure -> showAuthError(state.message)
            is AuthState.Failure -> showError(state.message)
            is AuthState.Loading -> showLoading()
            is AuthState.Success -> showData(state.data)
        }
    }

    private fun showLoading() {
        binding.loginLoader.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
        binding.loginInputLayout.isEnabled = false
        binding.passwordInputLayout.isEnabled = false
    }

    private fun showAuthError(message: String) {
        binding.loginLoader.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = message
        binding.loginInputLayout.isEnabled = true
        binding.passwordInputLayout.isEnabled = true
    }

    private fun showData(user: UserModel) {
        binding.loginLoader.visibility = View.GONE
        binding.loginInputLayout.isEnabled = true
        binding.passwordInputLayout.isEnabled = true
        openFragment(fragment = InfoFragment.newInstance(user))
    }

    private fun showError(message: String) {
        binding.loginLoader.visibility = View.GONE
        binding.loginInputLayout.isEnabled = true
        binding.passwordInputLayout.isEnabled = true
        binding.root.showSnackBar(message = message, length = Snackbar.LENGTH_SHORT)
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}