package ie.setu.getit.ui.component.listItem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.getit.R
import ie.setu.getit.ui.component.listings.ShowDeleteAlert
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    totalListed: Int,
    onClick: () -> Unit,
    isEditing: Boolean,
) {
    var showUpdateConfirmDialog by remember { mutableStateOf(false) }

    Button(
        modifier = modifier,
        onClick = {
            if (isEditing) {
                showUpdateConfirmDialog = true
            } else {
                onClick()
            }
        },
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),  // Make sure the Text takes up the full width of the Button
            text = if (isEditing) stringResource(id = R.string.updateButton) else stringResource(id = R.string.listButton),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center  // Ensure the text is centered
        )
    }
    Spacer(modifier.height(height = 16.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth(),  // Make sure the Text takes up the full width of the Button
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        text = "You have $totalListed listings"
    )
    if (showUpdateConfirmDialog) {
        ShowDeleteAlert(
            onDismiss = { showUpdateConfirmDialog = false },
            onDelete = onClick
        )
    }

}

@Composable
fun ShowUpdateAlert(
    onDismiss: () -> Unit,
    onUpdate: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(R.string.confirm_update)) },
        text = { Text(stringResource(R.string.confirm_update_message))},
        confirmButton = {
            Button(
                onClick = { onUpdate() }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
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
            isEditing = false,
            onClick = {}
        )
    }
}
