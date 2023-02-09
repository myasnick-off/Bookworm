package com.dev.miasnikoff.feature_auth.data

import com.dev.miasnikoff.core.model.UserModel
import io.reactivex.Maybe
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AuthService {
    fun validateAuth(login: String, pass: String): Maybe<UserModel> {
        return Maybe.create { emitter ->
            val num = (0..9).random()
            when {
                num < 7 -> {
                    if (login == MOCK_LOGIN && pass == MOCK_PASS) {
                        emitter.onSuccess(
                            UserModel(
                                name = "Dmitriy",
                                berthDate = SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).parse("21.10.1984"),
                                address = "Moscow, Svobodniy pr. 30",
                                email = "miasnikoff.dev@gmail.com"
                            )
                        )
                    } else {
                        emitter.onComplete()
                    }
                }
                else -> {
                    emitter.onError(IOException("Server error!"))
                }
            }
        }.delay(DEFAULT_DELAY_TIME, TimeUnit.MICROSECONDS)
    }

    companion object {
        private const val MOCK_LOGIN = "user"
        private const val MOCK_PASS = "password"
        private const val DEFAULT_DELAY_TIME = 2000L
    }
}