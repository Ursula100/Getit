package ie.setu.getit.ui.component.listItem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.getit.R
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    totalListed: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(16.dp)
    ) {
        /*Icon(Icons.Default.Add, contentDescription = "List")
        Spacer(modifier.width(width = 4.dp))*/
        Text(
            modifier = modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(R.string.listButton),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }
    Spacer(modifier.height(height = 36.dp))
    Text(
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        text = "You have $totalListed listings"
    )
}

@Preview(showBackground = true)
@Composable
fun ListButtonPreview() {
    GetitTheme {
        ListButton(
            Modifier,
            enabled = false,
            totalListed = 0,
            onClick = {}
        )
    }
}
