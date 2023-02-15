package com.dev.miasnikoff.feature_auth

import android.text.Editable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.miasnikoff.core.model.UserModel
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.feature_auth.domain.AuthRepository
import com.dev.miasnikoff.feature_auth.mock.MockEditable
import com.dev.miasnikoff.feature_auth.rule.RxRule
import com.dev.miasnikoff.feature_auth.ui.AuthState
import com.dev.miasnikoff.feature_auth.ui.LoginViewModel
import io.reactivex.Maybe
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.IOException

class LoginViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule: RxRule = RxRule()

    @Mock
    lateinit var authRepository: AuthRepository

    @Mock
    lateinit var router: FlowRouter

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var login: Editable
    private lateinit var pass: Editable

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(authRepository, router)
        login = MockEditable("login")
        pass = MockEditable("password")
    }

    @Test
    fun `should navigate back if login success`() {
        `when`(authRepository.checkAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onSuccess(UserModel()) })

        loginViewModel.getUser(login, pass)
        verify(router, times(1)).back()
    }

    @Test
    fun `should emit AuthFailure state if login failed`() {
        val observer = Observer<AuthState> {}
        val liveData = loginViewModel.liveData

        `when`(authRepository.checkAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onComplete() })

        try {
            liveData.observeForever(observer)
            loginViewModel.getUser(login, pass)

            val expectedVal = AuthState.AuthFailure("Wrong login or password!")
            val actualVal = liveData.value as AuthState.AuthFailure
            assertNotNull(liveData.value)
            assertSame(expectedVal.message, actualVal.message)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun `should emit Failure state if network failure happened`() {
        val observer = Observer<AuthState> {}
        val liveData = loginViewModel.liveData

        `when`(authRepository.checkAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onError(IOException("Server error!")) })

        try {
            liveData.observeForever(observer)
            loginViewModel.getUser(login, pass)

            val expectedVal = AuthState.Failure("Server error!")
            val actualVal = liveData.value as AuthState.Failure
            assertNotNull(liveData.value)
            assertSame(expectedVal.message, actualVal.message)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun `should send empty fields to repository when login and password are null`() {

        `when`(authRepository.checkAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onSuccess(UserModel()) })

        loginViewModel.getUser(null, null)
        verify(authRepository, times(1)).checkAuth("", "")
    }
}