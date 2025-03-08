package ie.setu.getit.ui.component.listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.getit.ui.theme.GetitTheme
import java.util.Date

@Composable
fun ListCard(
    id: Int,
    name: String,
    description: String,
    price: Int,
    location: String,
    listedON: Date
){
    ListItem(
        headlineContent = {
            Text(name)
        },
        overlineContent = {
            Text(id.toString())
        },
        supportingContent = {
            Column {
                Text(description)
                Spacer(Modifier.height(4.dp))
                Text(location)
                Spacer(Modifier.height(4.dp))
                Row {
                    Text(price.toString())
                    Spacer(Modifier.width(8.dp))
                    Text(listedON.toString())
                }
            }
        },
        trailingContent = {
            Icon(Icons.Default.MoreVert, contentDescription = "")
        },
        modifier = Modifier.padding(8.dp)
    )
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
fun ListCardPreview() {
    GetitTheme {
        ListCard(
            id = 12345,
            name = "Antique Bread Cutter",
            description = "1860's bread cutter. Cleaned and brought to usable state. Amazing old piece and craftmanship",
            price = 300,
            location = "Waterford, Ireland",
            listedON = Date()
        )
    }
}