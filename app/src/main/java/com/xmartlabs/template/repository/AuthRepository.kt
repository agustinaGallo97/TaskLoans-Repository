package com.xmartlabs.template.repository

import androidx.annotation.CheckResult
import com.xmartlabs.bigbang.core.extensions.applyIoSchedulers
import com.xmartlabs.template.model.Session
import com.xmartlabs.template.service.AuthService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val sessionRepository: SessionRepository
) {

  //TODO: Change signature and method to match authService request to fetch the Access Token
  val accessToken: Single<out Session>
    @CheckResult
    get() = authService.accessToken
        .applyIoSchedulers()
        .filter { authResponse -> authResponse.accessToken.isNotEmpty() }
        .toSingle()
        .map { Session(it.accessToken) }
        .doOnSuccess { sessionRepository.session = it }

  open fun signIn() = Completable.error(NotImplementedError())
}
