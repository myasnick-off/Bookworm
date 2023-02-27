package com.dev.miasnikoff.feature_auth.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_di.annotations.FlowNavHolder
import com.dev.miasnikoff.core_di.findFeatureExternalDeps
import com.dev.miasnikoff.core_navigation.FlowFragment
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.extensions.hideSoftKeyboard
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.dev.miasnikoff.feature_auth.R
import com.dev.miasnikoff.feature_auth.databinding.FragmentLoginBinding
import com.dev.miasnikoff.feature_auth.di.AuthFeatureComponentExternalDepsProvider
import com.dev.miasnikoff.feature_auth.di.AuthFeatureComponentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.fragment_login), FlowFragment {

    @Inject
    @FlowNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    override lateinit var binding: FragmentLoginBinding
    private var disposables = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        AuthFeatureComponentExternalDepsProvider.featureExternalDeps = findFeatureExternalDeps()
        viewModel<AuthFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        initView()
        initMenu()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.bind(findNavController())
    }

    override fun onPause() {
        navigatorHolder.unbind()
        super.onPause()
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
            binding.root.hideSoftKeyboard()
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
                    textInputLayout.error = getString(com.dev.miasnikoff.core_ui.R.string.required_field)
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

    private fun showError(message: String) {
        binding.loginLoader.visibility = View.GONE
        binding.loginInputLayout.isEnabled = true
        binding.passwordInputLayout.isEnabled = true
        binding.root.showSnackBar(message = message, length = Snackbar.LENGTH_SHORT)
    }
}