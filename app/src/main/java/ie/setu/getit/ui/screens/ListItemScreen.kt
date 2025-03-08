package ie.setu.getit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import timber.log.Timber

@Composable
fun ListItemScreen(
    modifier: Modifier = Modifier,
    listings: SnapshotStateList<ItemModel>,
    paddingValues: PaddingValues
) {

    var totalListed by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    var location by remember { mutableStateOf("") }

    //Field check - ensure not empty (not yet taking into consideration non int values for price field and spaces)
    var isNameError by remember { mutableStateOf(false) }
    var isDescError by remember { mutableStateOf(false) }
    var isPriceError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }

    //Button check - enabled state
    val isButtonEnabled = !isNameError && !isDescError && !isPriceError && !isLocationError

    // Handle input changes and error state updates
    fun onNameChange(newName: String) {
        name = newName
        isNameError = newName.isBlank()
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        isDescError = newDescription.isBlank()
    }

    fun onPriceChange(newPrice: String) {
        price = newPrice.toInt()
        isPriceError = newPrice.toInt() == 0 && newPrice.isBlank()
    }

    fun onLocationChange(newLocation: String) {
        location = newLocation
        isLocationError = newLocation.isBlank()
    }

    val onList: () -> Unit = {
        isNameError = name.isBlank()
        isDescError = description.isBlank()
        isPriceError = price == 0
        isLocationError = location.isBlank()

        if (isNameError || isDescError || isPriceError || isLocationError) {
            // Show feedback or an alert here

        } else {
            // Proceed with your action (e.g., adding the item)
            val newItem = ItemModel(
                name = name,
                description = description,
                price = price,
                location = location
            )
            listings.add(newItem)
            Timber.i("New Listing info : $newItem")
            totalListed+=1
            Timber.i("All Listings ${listings.toList()}")
        }
    }

    Column {
        Column(
            modifier = modifier.padding(
                top = paddingValues.calculateTopPadding() + 30.dp, // Extra space
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            NameInput(
                modifier = modifier,
                name = name,
                onNameChange = { onNameChange(it) },
                isError = isNameError
            )
            DescriptionInput(
                modifier = modifier,
                description = description,
                onDescriptionChange = { onDescriptionChange(it)},
                isError = isDescError
            )
            PriceInput(
                modifier = modifier,
                price = price.toString(),
                onPriceChange = { onPriceChange(it.toString())},
                isError = isPriceError
            )
            LocationInput(
                modifier = modifier,
                location = location,
                onLocationChange = { onLocationChange(it) },
                isError = isLocationError
            )
            Spacer(modifier.height(height = 24.dp))
        }
        ListButton(
            modifier = modifier.align(Alignment.CenterHorizontally),
            enabled = isButtonEnabled,
            onClick = onList,
            totalListed = totalListed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GetitTheme {
        ListItemScreen(
            modifier = Modifier,
            listings = fakeListings.toMutableStateList(),
            paddingValues = PaddingValues()
        )
    }
}
