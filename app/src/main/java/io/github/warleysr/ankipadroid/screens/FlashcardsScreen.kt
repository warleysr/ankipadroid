package io.github.warleysr.ankipadroid.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.warleysr.ankipadroid.AnkiDroidHelper
import io.github.warleysr.ankipadroid.R
import io.github.warleysr.ankipadroid.viewmodels.AnkiDroidViewModel
import io.github.warleysr.ankipadroid.viewmodels.PronunciationViewModel
import io.github.warleysr.ankipadroid.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun FlashcardsScreen(
    settingsViewModel: SettingsViewModel,
    pronunciationViewModel: PronunciationViewModel,
    ankiDroidViewModel: AnkiDroidViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    var success by remember { mutableStateOf(false) }
    var currentStatus by remember { mutableStateOf("Click here") }
    var currentInput by remember { mutableStateOf("How are you?") }
    var performing by remember { mutableStateOf(false) }
    var recording by remember { mutableStateOf(false) }

    var deckSelected by remember { mutableStateOf(false) }
    var currentQuestion by remember { mutableStateOf("Waiting...") }
    var currentAnswer by remember { mutableStateOf("Waiting...") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (deckSelected || ankiDroidViewModel.isDeckSelected) {
                ankiDroidViewModel.queryNextCard(
                    onResult = {question, answer ->
                        currentQuestion = question
                        currentAnswer = answer
                    }
                )
                Text(currentQuestion, style = MaterialTheme.typography.bodyMedium)
                Text(currentAnswer, style = MaterialTheme.typography.bodyMedium)
            } else {
                Text("Select a deck", style = MaterialTheme.typography.headlineMedium)

                ankiDroidViewModel.getDeckList()?.forEach { deck ->
                    Button(onClick = {
                        ankiDroidViewModel.selectDeck(deck)
                        deckSelected = true
                    }) {
                        Text(deck)
                    }
                }
            }
        }
//        if (!success) {
//            Column {
//
//                OutlinedTextField(
//                    currentInput,
//                    label = { Text("Text to pronounce") },
//                    onValueChange = {
//                        currentInput = it
//                    },
//                    enabled = !performing
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Row (
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Button(
//                        enabled = !performing,
//                        onClick = {
//                            coroutineScope.launch {
//                                performing = true
//                                val azureKey = settingsViewModel.getSetting("azure_key")
//                                val language = settingsViewModel.getSetting("language")
//                                val region = settingsViewModel.getSetting("region")
//
//                                pronunciationViewModel.newAssessment(
//                                    currentInput,
//                                    language = language,
//                                    speechApiKey = azureKey,
//                                    speechRegion = region,
//                                    onResult = { result ->
//                                        success = result
//                                        currentStatus = if (result) "OK" else "Canceled"
//                                        performing = false
//                                    }
//                                )
//                            }
//                        }
//                    ) {
//                        Text(text = currentStatus)
//                    }
//                    Button (
//                        enabled = !performing,
//                        onClick = {
//                            recording = !recording
//                            if (recording) {
//                                pronunciationViewModel.startRecording()
//                            }
//                            else {
//                                pronunciationViewModel.stopRecording()
//                            }
//                        }
//                    ) {
//                        Text(if (recording) "Stop" else "Record")
//                    }
//                }
//            }
//        } else {
//            PronunciationAssessmentResults(pronunciationViewModel)
//        }
    }
}