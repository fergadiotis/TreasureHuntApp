package com.tassos.treasurehuntapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tassos.treasurehuntapp.ui.theme.TreasureHuntAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntAppTheme {
                SplashScreen {
                    // Launch MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onStartClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1), // You'll need to add a logo image
                contentDescription = "App Logo",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "City Treasure Hunt",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Explore the city. Find all 22 locations. Win a prize!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 7.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = onStartClick) {
                Text("Start Hunt")
            }
        }
    }
}