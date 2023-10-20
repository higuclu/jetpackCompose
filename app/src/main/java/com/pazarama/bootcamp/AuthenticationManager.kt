package com.pazarama.bootcamp

class AuthenticationManager(
    private val onSuccess: () -> Unit,
    private val onFailure: () -> Unit,
    private val onMissing: () -> Unit
) {

    fun performAuthentication(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            if (username == "admin" && password == "123") {
                onSuccess()
            } else {
                onFailure()
            }
        } else {
            onMissing()
        }
    }
}
