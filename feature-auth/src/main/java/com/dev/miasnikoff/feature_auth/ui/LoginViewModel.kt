package com.dev.miasnikoff.feature_auth.ui

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.feature_auth.domain.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _liveData: MutableLiveData<AuthState> = MutableLiveData()
    val liveData: LiveData<AuthState> get() = _liveData

    private var disposables = CompositeDisposable()

    fun getUser(login: Editable?, pass: Editable?) {
        disposables.add(
            authRepository.checkAuth(checkLogin(login), checkPassword(pass))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _liveData.value = AuthState.Loading
                }
                .doOnComplete {
                    _liveData.value = AuthState.AuthFailure(message = "Wrong login or password!")
                }
                .subscribe(
                    { user -> _liveData.value = AuthState.Success(data = user) },
                    { err -> _liveData.value = AuthState.Failure(err.message ?: "Unknown error!") }
                )
        )
    }

    private fun checkLogin(login: Editable?): String {
        return if (login.isNullOrBlank()) "" else login.toString()
    }

    private fun checkPassword(pass: Editable?): String {
        return if (pass.isNullOrBlank()) "" else pass.toString()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}