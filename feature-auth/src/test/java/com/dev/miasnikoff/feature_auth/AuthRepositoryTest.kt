package com.dev.miasnikoff.feature_auth

import com.dev.miasnikoff.core.model.UserModel
import com.dev.miasnikoff.feature_auth.data.AuthRepositoryImpl
import com.dev.miasnikoff.feature_auth.data.AuthService
import com.dev.miasnikoff.feature_auth.domain.AuthRepository
import com.dev.miasnikoff.feature_auth.rule.RxRule
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AuthRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxRule = RxRule()

    @Mock
    lateinit var authService: AuthService

    lateinit var authRepository: AuthRepository
    private lateinit var login: String
    private lateinit var pass: String
    lateinit var userData: UserModel

    @Before
    fun setup() {
        authRepository = AuthRepositoryImpl(authService)
        login = "user"
        pass = "password"
        userData = UserModel(
            name = "Dmitriy",
            berthDate = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).parse("21.10.1984"),
            address = "Moscow, Svobodniy pr. 30",
            email = "miasnikoff.dev@gmail.com"
        )
    }

    @Test
    fun `should emit user data if login success`() {

        `when`(authService.validateAuth(login, pass))
            .thenReturn(Maybe.create { it.onSuccess(userData) })

        authRepository.checkAuth(login, pass)
            .test()
            .assertResult(userData)
            .assertNoErrors()
    }

    @Test
    fun `should emit complete if login failed`() {
        `when`(authService.validateAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onComplete() })

        authRepository.checkAuth(anyString(), anyString())
            .test()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `should emit error if network failure happened`() {
        `when`(authService.validateAuth(anyString(), anyString()))
            .thenReturn(Maybe.create { it.onError(IOException("Server error!")) })

        authRepository.checkAuth(anyString(), anyString())
            .test()
            .assertError(IOException::class.java)
            .assertErrorMessage("Server error!")
    }
}