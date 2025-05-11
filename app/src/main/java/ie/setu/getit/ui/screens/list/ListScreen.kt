package ie.setu.getit.ui.screens.list

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ie.setu.getit.R
import ie.setu.getit.data.model.Category
import ie.setu.getit.data.model.ItemCondition
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.model.fakeListings
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.ui.component.listItem.DescriptionInput
import ie.setu.getit.ui.component.listItem.ListButton
import ie.setu.getit.ui.component.listItem.LocationInput
import ie.setu.getit.ui.component.listItem.TitleInput
import ie.setu.getit.ui.component.listItem.PriceInput
import ie.setu.getit.ui.component.navigation.MyListings
import ie.setu.getit.ui.screens.listings.ListingsViewModel
import ie.setu.getit.ui.theme.GetitTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    listingsViewModel: ListingsViewModel = hiltViewModel(),
    listViewModel: ListViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavHostController,
    authService: AuthService,
    id: String? = null
) {
    val context = LocalContext.current
    val listings = listingsViewModel.uiListings.collectAsState().value

    val uid = authService.getCurrentUserId()!! // trusting uid is always be available if user is logged in

    // check and determine if in edit mode
    val listingToEdit = if (id != null) listViewModel.listing.value else null
    val isEditing = listingToEdit != null

    // Form state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    var location by remember { mutableStateOf("") }
    var selectedCondition by remember { mutableStateOf(ItemCondition.NEW) }
    var isConditionDropdownExpanded by remember { mutableStateOf(false) }
    val selectedCategories = remember { mutableStateListOf<Category>() }

    // Error state
    var isNameError by remember { mutableStateOf(false) }
    var isDescError by remember { mutableStateOf(false) }
    var isPriceError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }

    // Only update when listingToEdit changes
    LaunchedEffect(listingToEdit) {
        listingToEdit?.let {
            title = it.title
            description = it.description
            price = it.price
            location = it.location
            selectedCondition = it.condition
            selectedCategories.clear()
            selectedCategories.addAll(it.categories)
        }
    }


    val isButtonEnabled = !isNameError && !isDescError && !isPriceError && !isLocationError

    // Input change handlers
    fun onTitleChange(newName: String) {
        title = newName
        isNameError = title.isBlank()
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        isDescError = description.isBlank()
    }

    fun onPriceChange(newPrice: String) {
        price = newPrice.toIntOrNull() ?: 0
        isPriceError = price <= 0
    }

    fun onLocationChange(newLocation: String) {
        location = newLocation
        isLocationError = location.isBlank()
    }

    val onSubmit: () -> Unit = {
        isNameError = title.isBlank()
        isDescError = description.isBlank()
        isPriceError = price <= 0
        isLocationError = location.isBlank()

        if (isNameError || isDescError || isPriceError || isLocationError) {
            Toast.makeText(context, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
        } else if (selectedCategories.isEmpty()) {
            Toast.makeText(context, "You can select at least 1 category", Toast.LENGTH_SHORT).show()
        } else {
            val newItem = listingToEdit?.copy(
                title = title,
                description = description,
                price = price,
                location = location,
                condition = selectedCondition,
                categories = selectedCategories.toList()
            ) ?: ListingModel(
                title = title,
                description = description,
                price = price,
                location = location,
                condition = selectedCondition,
                categories = selectedCategories.toList(),
                uid = uid,
            )

            if (isEditing) {
                listViewModel.update(newItem)
            } else {
                listViewModel.insert(newItem)
            }

            navController.navigate(MyListings.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item {
            TitleInput(
                modifier = modifier,
                title = title,
                onNameChange = ::onTitleChange,
                isError = isNameError
            )
        }

        item {
            DescriptionInput(
                modifier = modifier,
                description = description,
                onDescriptionChange = ::onDescriptionChange,
                isError = isDescError
            )
        }

        item {
            PriceInput(
                modifier = modifier,
                price = price.toString(),
                onPriceChange = ::onPriceChange,
                isError = isPriceError
            )
        }

        item {
            LocationInput(
                modifier = modifier,
                location = location,
                onLocationChange = ::onLocationChange,
                isError = isLocationError
            )
        }

        item {
            Column {
                ExposedDropdownMenuBox(
                    expanded = isConditionDropdownExpanded,
                    onExpandedChange = { isConditionDropdownExpanded = !isConditionDropdownExpanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedCondition.name.replace("_", " ").lowercase()
                            .split(" ")
                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                        onValueChange = {},
                        label = { Text("Select Item Condition") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isConditionDropdownExpanded)
                        },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isConditionDropdownExpanded,
                        onDismissRequest = { isConditionDropdownExpanded = false }
                    ) {
                        ItemCondition.entries.forEach { condition ->
                            DropdownMenuItem(
                                text = {
                                    Text(condition.name.replace("_", " ").lowercase()
                                        .split(" ")
                                        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } })
                                },
                                onClick = {
                                    selectedCondition = condition
                                    isConditionDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = modifier.fillMaxWidth()) {
                Text(text = "Categories (select at least 1 and up to 5)")
                Category.entries.forEach { category ->
                    val isChecked = category in selectedCategories
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    if (selectedCategories.size < 5) {
                                        selectedCategories.add(category)
                                    } else {
                                        Toast.makeText(context, "You can only select up to 5 categories", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    selectedCategories.remove(category)
                                }
                            }
                        )
                        Text(text = category.name.replace("_", " ")
                            .lowercase()
                            .split(" ")
                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } })
                    }
                }
            }
        }

        item {
            ListButton(
                modifier = modifier.fillMaxWidth(),
                enabled = isButtonEnabled,
                onClick = onSubmit,
                totalListed = listings.size,
                isEditing = isEditing
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GetitTheme {
        PreviewListScreen(
            modifier = Modifier,
            listings = fakeListings.toMutableStateList(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewListScreen(modifier: Modifier = Modifier, listings: SnapshotStateList<ListingModel>){
    val context = LocalContext.current  // for the Toast

    var totalListed = listings.size

    var title by remember { mutableStateOf("Brand new retro Air Jordan's") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableIntStateOf(0) }
    var location by remember { mutableStateOf("") }

    // Dropdown state for condition
    var selectedCondition by remember { mutableStateOf(ItemCondition.NEW) }
    var isConditionDropdownExpanded by remember { mutableStateOf(false) }

    // Checkbox state for categories
    val selectedCategories = remember { mutableStateListOf<Category>() }

    // Field check - ensure not empty
    var isNameError by remember { mutableStateOf(false) }
    var isDescError by remember { mutableStateOf(false) }
    var isPriceError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }

    // Button check - enabled state
    val isButtonEnabled = !isNameError && !isDescError && !isPriceError && !isLocationError

    // Handle input changes and error state updates
    fun onTitleChange(newName: String) {
        title = newName
        isNameError = title.isBlank()
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        isDescError = description.isBlank()
    }

    fun onPriceChange(newPrice: String) {
        if (newPrice.isBlank()) {
            price = 0
            isPriceError = true
        } else {
            price = newPrice.toIntOrNull() ?: 0
            isPriceError = price == 0
        }
    }

    fun onLocationChange(newLocation: String) {
        location = newLocation
        isLocationError = location.isBlank()
    }

    val onList: () -> Unit = {
        isNameError = title.isBlank()
        isDescError = description.isBlank()
        isPriceError = price == 0
        isLocationError = location.isBlank()

        if (isNameError || isDescError || isPriceError || isLocationError) {
            Toast.makeText(context, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
        }
        else if (selectedCategories.size <1 ) {
            Toast.makeText(context, "You can select at least 1 category", Toast.LENGTH_SHORT).show()
        }
        else {
            val newItem = ListingModel(
                title = title,
                description = description,
                price = price,
                location = location,
                condition = selectedCondition,
                categories = selectedCategories.toList(),
                uid = "1",
            )
            listings.add(newItem)
            Timber.i("New Listing info : $newItem")
            totalListed += 1
            Timber.i("All Listings ${listings.toList()}")
        }
    }

    // make the screen scrollable
    LazyColumn(
        modifier = modifier
            .padding( start = 24.dp, end = 24.dp)
            .fillMaxSize(), // Ensure the LazyColumn takes up the full available space
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item {
            TitleInput(
                modifier = modifier,
                title = title,
                onNameChange = { onTitleChange(it) },
                isError = isNameError
            )
        }

        item {
            DescriptionInput(
                modifier = modifier,
                description = description,
                onDescriptionChange = { onDescriptionChange(it) },
                isError = isDescError
            )
        }

        item {
            PriceInput(
                modifier = modifier,
                price = price.toString(),
                onPriceChange = { onPriceChange(it) },
                isError = isPriceError
            )
        }

        item {
            LocationInput(
                modifier = modifier,
                location = location,
                onLocationChange = { onLocationChange(it) },
                isError = isLocationError
            )
        }

        item {
            // Dropdown for ItemCondition
            Column {
                ExposedDropdownMenuBox(
                    expanded = isConditionDropdownExpanded,
                    onExpandedChange = { isConditionDropdownExpanded = !isConditionDropdownExpanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedCondition.name.replace("_", " ")
                            .lowercase()
                            .split(" ")
                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }, //capitalise first character of each word
                        onValueChange = {},
                        label = { Text("Select Item Condition") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isConditionDropdownExpanded
                            )
                        },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isConditionDropdownExpanded,
                        onDismissRequest = { isConditionDropdownExpanded = false }
                    ) {
                        ItemCondition.entries.forEach { condition ->
                            DropdownMenuItem(
                                text = { Text(condition.name.replace("_", " ")
                                    .lowercase()
                                    .split(" ")
                                    .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } } //capitalise first character of each word
                                ) },
                                onClick = {
                                    selectedCondition = condition
                                    isConditionDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            // Category checkboxes (limit to 5)
            Column (modifier = modifier.fillMaxWidth()) {
                Text(text = "Categories (select at least 1 and up to 5)")
                Category.entries.forEach { category ->
                    val isChecked = category in selectedCategories
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    if (selectedCategories.size < 5) {
                                        selectedCategories.add(category)
                                    } else {
                                        Toast.makeText(context, "You can only select up to 5 categories", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    selectedCategories.remove(category)
                                }
                            }
                        )
                        Text(text = category.name.replace("_", " ")
                            .lowercase()
                            .split(" ")
                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() }
                            })
                    }
                }
            }
        }

        item {
            ListButton(
                modifier = modifier.fillMaxWidth(),
                enabled = isButtonEnabled,
                onClick = onList,
                totalListed = totalListed,
                isEditing = false
            )
        }
    }
}
