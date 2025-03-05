package ie.setu.getit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.getit.ui.component.list.DescriptionInput
import ie.setu.getit.ui.component.list.ListButton
import ie.setu.getit.ui.component.list.LocationInput
import ie.setu.getit.ui.component.list.NameInput
import ie.setu.getit.ui.component.list.PriceInput
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListScreen(modifier: Modifier = Modifier,
               listings: SnapshotStateList<ItemModel>
) {

    var totalListed by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(10) }
    var location by remember { mutableStateOf("") }

    Column {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            ProgressBar(
                modifier = modifier,
                totalListed = totalDonated)
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            NameInput (
                modifier = modifier,
                onNameChange = { name = it }
            )
            DescriptionInput (
                modifier = modifier,
                onDescriptionChange = { description = it }
            )
            PriceInput (
                modifier = modifier,
                onPriceChange = { price = it }
            )
            LocationInput (
                modifier = modifier,
                onLocationChange = { location = it }
            )
            ListButton (
                modifier = modifier,
                item = ItemModel(name = name,
                    description = description,
                    price = price,
                    location = location),
                listings = listings,
                onTotalListedChange = { totalListed = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GetitTheme {
        ListScreen( modifier = Modifier,
            listings = fakeListings.toMutableStateList())
    }
}
