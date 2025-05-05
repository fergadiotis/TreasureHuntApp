package com.tassos.treasurehuntapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tassos.treasurehuntapp.ui.theme.TreasureHuntAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntAppTheme {
                TreasureHuntApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureHuntApp(
    viewModel: TreasureHuntViewModel = viewModel()
) {
    val currentStep by viewModel.currentStep.collectAsState()
    val locations by viewModel.locations.collectAsState()
    val totalSteps = locations.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Offline Treasure Hunt") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (locations.isNotEmpty()) {
                Text(
                    "Clue ${currentStep + 1} of $totalSteps",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display current location and description
                val currentLocation = locations[currentStep]
                Text(
                    "Go to: ${currentLocation.name}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    currentLocation.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.city_map),
                    contentDescription = "City Map",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.nextLocation() },
                    enabled = currentStep < totalSteps - 1
                ) {
                    Text("I found it!")
                }

                if (currentStep == totalSteps - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "🎉 You've completed the treasure hunt! Enter the draw now!",
                        color = Color.Green,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.resetHunt() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text("Start New Hunt")
                    }
                }
            }
        }
    }
}