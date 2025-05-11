package ie.setu.getit.ui.component.register

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CountryCodeDropdown(selectedCode: String, onCodeChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val codes = listOf("+353", "+44", "+1", "+234", "+33")

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCode)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            codes.forEach { code ->
                DropdownMenuItem(
                    text = { Text(code) },
                    onClick = {
                        onCodeChange(code)
                        expanded = false
                    }
                )
            }
        }
    }
}