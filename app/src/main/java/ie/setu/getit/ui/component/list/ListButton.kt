package ie.setu.getit.ui.component.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import ie.setu.getit.ui.theme.GetitTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.getit.R

@Composable
fun ListButton(
    modifier: Modifier = Modifier,
    item: ItemModel,
    listings: SnapshotStateList<ItemModel>,
    onTotalListedChange: (Int) -> Unit
) {
    var totalListed by remember { mutableIntStateOf(0) }

    Column{
        Button(
            onClick = {
                totalListed+=1
                onTotalListedChange(totalListed)
                listings.add(item)
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "List")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.listButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier.weight(1f))
        Text( text = "Total Listed : $totalListed")
    }
}

@Preview(showBackground = true)
@Composable
fun ListButtonPreview() {
    GetitTheme {
        ListButton(
            Modifier,
            ItemModel(),
            listings = fakeListings.toMutableStateList()
        ) {}
    }
}
