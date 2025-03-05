package ie.setu.getit.ui.component.list

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
fun DescriptionInput(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {

    var desc by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 3,
        value = desc,
        onValueChange = {
            desc = it
            onDescriptionChange(desc)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_desc)) },
        supportingText = { Text(stringResource(R.string.enter_desc_support_text)) }
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    GetitTheme {
        DescriptionInput(
            Modifier,
            onDescriptionChange = {})
    }
}