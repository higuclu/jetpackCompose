package com.pazarama.bootcamp

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test

class AuthenticationManagerTest {

    @Test
    fun `performAuthentication with valid credentials should call onSuccess`() {
        // Arrange
        val onSuccessMock: () -> Unit = mockk()
        val authenticationManager = AuthenticationManager(onSuccessMock, {}, {})
        val username = "admin"
        val password = "123"
        every { onSuccessMock.invoke() } just runs

        // Act
        authenticationManager.performAuthentication(username, password)

        // Assert
        verify(exactly = 1) { onSuccessMock.invoke() }
    }

    @Test
    fun `performAuthentication with invalid credentials should call onFailure`() {
        // Arrange
        val onFailureMock: () -> Unit = mockk()
        val authenticationManager = AuthenticationManager({}, onFailureMock, {})
        val username = "invalid_user"
        val password = "invalid_password"
        every { onFailureMock.invoke() } just runs

        // Act
        authenticationManager.performAuthentication(username, password)

        // Assert
        verify(exactly = 1) { onFailureMock.invoke() }
    }

    @Test
    fun `performAuthentication with missing credentials should call onMissing`() {
        // Arrange
        val onMissingMock: () -> Unit = mockk()
        val authenticationManager = AuthenticationManager({}, {}, onMissingMock)
        val username = ""
        val password = ""
        every { onMissingMock.invoke() } just runs

        // Act
        authenticationManager.performAuthentication(username, password)

        // Assert
        verify(exactly = 1) { onMissingMock.invoke() }
    }
}
