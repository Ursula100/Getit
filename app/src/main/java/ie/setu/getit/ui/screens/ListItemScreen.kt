package ie.setu.getit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.getit.data.ItemModel
import ie.setu.getit.data.fakeListings
import ie.setu.getit.ui.component.listItem.DescriptionInput
import ie.setu.getit.ui.component.listItem.ListButton
import ie.setu.getit.ui.component.listItem.LocationInput
import ie.setu.getit.ui.component.listItem.NameInput
import ie.setu.getit.ui.component.listItem.PriceInput
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListItemScreen(modifier: Modifier = Modifier,
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
                top = 72.dp,
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            NameInput(
                modifier = modifier,
                onNameChange = { name = it }
            )
            DescriptionInput(
                modifier = modifier,
                onDescriptionChange = { description = it }
            )
            PriceInput(
                modifier = modifier,
                onPriceChange = { price = it }
            )
            LocationInput(
                modifier = modifier,
                onLocationChange = { location = it }
            )
            Spacer(modifier.height(height = 16.dp))
        }
        ListButton(
            modifier = modifier.align(Alignment.CenterHorizontally),
            item = ItemModel(
                name = name,
                description = description,
                price = price,
                location = location
            ),
            listings = listings,
            onTotalListedChange = { totalListed = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GetitTheme {
        ListItemScreen( modifier = Modifier,
            listings = fakeListings.toMutableStateList())
    }
}
