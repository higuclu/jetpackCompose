package com.pazarama.bootcamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GUI()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GUI() {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarText by remember { mutableStateOf("") }
    val successMessage = stringResource(id = R.string.snackbar_text_success)
    val failMessage = stringResource(id = R.string.snackbar_text_fail)
    val missingMessage = stringResource(id = R.string.snackbar_text_missing_field)

    val authenticationManager = AuthenticationManager(
        onSuccess = {
            snackbarText = successMessage
            snackbarVisible = true
        },
        onFailure = {
            snackbarText = failMessage
            snackbarVisible = true
        },
        onMissing = {
            snackbarText = missingMessage
            snackbarVisible = true
        }
    )

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            delay(3000) // Snackbar visibility time
            snackbarVisible = false
        }
    }
//Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        TextField(
            value = username.text,
            onValueChange = { username = TextFieldValue(it) },
            label = { Text(stringResource(id = R.string.label_username)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("usernameField"),
        )

        TextField(
            value = password.text,
            onValueChange = { password = TextFieldValue(it) },
            label = { Text(stringResource(id = R.string.label_password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("passwordField"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authenticationManager.performAuthentication(username.text, password.text)
            },
            modifier = Modifier
                .testTag("loginButton")
        ) {
            Text(stringResource(id = R.string.button_login))
        }
    }

    if (snackbarVisible) {
        // Show snackbar
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {},
        ) {
            Text(text = snackbarText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    GUI()
}
