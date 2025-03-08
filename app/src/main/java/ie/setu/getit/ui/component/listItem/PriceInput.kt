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
fun PriceInput(
    modifier: Modifier = Modifier,
    price: String,
    onPriceChange: (String) -> Unit,
    isError: Boolean
) {

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 1,
        value = price,
        onValueChange = {
            onPriceChange(it)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_price)) },
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun PricePreview() {
    GetitTheme {
        PriceInput(
            Modifier,
            price = "100",
            onPriceChange = {},
            isError = false)
    }
}