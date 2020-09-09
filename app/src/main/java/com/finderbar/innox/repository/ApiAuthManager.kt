package com.finderbar.innox.repository

import com.google.gson.Gson

//interface OnAccountStatusChangeListener {
//    fun onLogin(user: User)
//
//    fun onLogout()
//}

//object AccountManager {
//
//    var username by pref("")
//    var password by pref("")
//    var authId by pref(-1)
//    var token by pref("")
//
//    var userJson by pref("")
//
//    var currentUser: User? = null
//        get() {
//            if (field == null && userJson.isNotEmpty()) {
//                field = Gson().fromJson<User>(userJson)
//            }
//            return field
//        }
//        set(value) {
//            if (value == null) {
//                userJson = ""
//            } else {
//                userJson = Gson().toJson(value)
//            }
//
//            field = value
//        }
//
//    var onAccountStatusChangeListenerList = ArrayList<OnAccountStatusChangeListener>()
//
//    private fun notifyLogin(user: User) {
//        onAccountStatusChangeListenerList.forEach {
//            it.onLogin(user)
//        }
//    }
//
//    private fun notifyLogout() {
//        onAccountStatusChangeListenerList.forEach {
//            it.onLogout()
//        }
//    }
//
//    fun isLoginIn(): Boolean = token.isNotEmpty()
//
//    fun login2() =
//        AuthService.getUser()
//            .doOnNext {
//                if (it.token == null || it.token.isEmpty()) it.token = Configs.Account.accessToken
//            }
//            .flatMap {
//                token = it.token
//                authId = it.id
//                UserService.getAuthenticatedUser()
//            }
//            .map {
//                currentUser = it
//                notifyLogin(it)
//            }
//
//    fun login() =
//        AuthService.createAuthorization(AuthorizationReq())
//            .doOnNext {
//                if (token.isEmpty()) throw AccountException(it)
//            }
//            .retryWhen {
//                it.flatMap {
//                    if (it is AccountException) {
//                        AuthService.deleteAuthorization(it.authorizationRsp.id)
//                    } else {
//                        Observable.error(it)
//                    }
//                }
//            }
//            .flatMap {
//                token = it.token
//                authId = it.id
//                UserService.getAuthenticatedUser()
//            }
//            .map {
//                currentUser = it
//                notifyLogin(it)
//            }
//
//    fun logout() = AuthService.deleteAuthorization(authId)
//        .doOnNext {
//            if (it.isSuccessful) {
//                currentUser = null
//                authId = -1;
//                token = ""
//                notifyLogout()
//            } else {
//                throw HttpException(it)
//            }
//
//        }
//
//    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")
//
//    fun logoutDebug(){
//        currentUser = null
//        authId = -1;
//        token = ""
//        notifyLogout()
//    }
//
//}
