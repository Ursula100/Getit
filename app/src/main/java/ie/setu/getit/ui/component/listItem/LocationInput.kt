package ie.setu.getit.ui.component.listItem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.getit.R
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun LocationInput(
    modifier: Modifier = Modifier,
    location: String,
    onLocationChange: (String) -> Unit,
    isError: Boolean
) {

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 1,
        value = location,
        onValueChange = {
            onLocationChange(it)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_location)) },
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    GetitTheme {
        LocationInput(
            Modifier,
            location = "Waterford, Eire",
            onLocationChange = {},
            isError = false)
    }
}