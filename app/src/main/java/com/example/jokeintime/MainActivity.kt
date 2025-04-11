package com.example.jokeintime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jokeintime.data.model.JokeResponse
import com.example.jokeintime.ui.theme.JokeInTimeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokeInTimeTheme {
                JokeApp()
            }
        }
    }
}

@Composable
fun JokeApp() {
    val coroutineScope = rememberCoroutineScope()
    var jokeText by remember { mutableStateOf("Está querendo sorrir hoje?") }
    var isLoading by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Joke in Time",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val joke: JokeResponse = RetrofitInstance.api.getJoke()
                            jokeText = if (joke.type == "single") {
                                joke.joke ?: "Sem piada recebida"
                            } else {
                                "${joke.setup}\n\n${joke.delivery}"
                            }
                        } catch (e: Exception) {
                            jokeText = "Erro ao buscar piada: $e"
                        }
                        isLoading = false
                    }
                }
            ) {
                Text(if (isLoading) "Carregando..." else "Me conte uma piada")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = jokeText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

