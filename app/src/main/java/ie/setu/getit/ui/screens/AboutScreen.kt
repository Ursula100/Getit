package ie.setu.getit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // Light background color
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Welcome to GetIt!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Description
            Text(
                text = "GetIt is your go-to app for buying and selling rare gems! 🌟\n" +
                        "List your unique items and get the best bids!\n\n" +
                        "Whether you're a seller looking for the best price 💰 or a buyer searching for hidden gems 🔎, GetIt is here to make it happen! 💎\n\n" +
                        "Join our community today and start discovering treasures!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            // Icon: Star Icon from Material Design Icons
            Spacer(modifier = Modifier.height(24.dp))

            // Call to action or encouragement text
            Text(
                text = "Start listing today! 📲",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    GetitTheme {
        AboutScreen(modifier = Modifier)
    }
}
