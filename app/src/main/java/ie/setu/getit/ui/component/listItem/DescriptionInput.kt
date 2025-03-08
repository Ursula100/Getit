package ie.setu.getit.ui.component.listItem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.getit.R
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun DescriptionInput(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionChange: (String) -> Unit,
    isError: Boolean
) {

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 3,
        value = description,
        onValueChange = {
            onDescriptionChange(it)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_desc)) },
        isError = isError,
        supportingText = { Text(stringResource(R.string.enter_desc_support_text)) }
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    GetitTheme {
        DescriptionInput(
            Modifier,
            description = "Antique Butcher knife set",
            onDescriptionChange = {},
            isError = false )
    }
}