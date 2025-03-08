package ie.setu.getit.ui.component.listItem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.getit.R
import ie.setu.getit.data.ItemModel
import ie.setu.getit.data.fakeListings
import ie.setu.getit.ui.theme.GetitTheme
import timber.log.Timber

@Composable
fun ListButton(
    modifier: Modifier = Modifier,
    item: ItemModel,
    listings: SnapshotStateList<ItemModel>,
    onTotalListedChange: (Int) -> Unit
) {
    var totalListed by remember { mutableIntStateOf(0) }

    Button(
        modifier = modifier,
        onClick = {
            totalListed+=1
            onTotalListedChange(totalListed)
            listings.add(item)
            Timber.i("New Listing info : $item")
            Timber.i("All Listings ${listings.toList()}")
        },
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
            ItemModel(),
            listings = fakeListings.toMutableStateList()
        ) {}
    }
}
