package ie.setu.getit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "listingmodel")
data class ListingModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String,
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    val location: String = "",
    val condition: ItemCondition = ItemCondition.NEW,
    val categories: List<Category> = emptyList(),
    val listedOn: Date = Date()
)

val fakeListings = List(30) { i ->
    ListingModel(id = 12345 + i,
        uid = "1",
        "Item $i",
        "N/A",
        i,
        "Eire",
        condition = ItemCondition.REFURBISHED,
        categories = listOf(Category.CLOTHING, Category.SPORTS),
        Date()
    )
}
