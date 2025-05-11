package ie.setu.getit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "listingmodel")
data class ListingModel(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val uid: String = "",
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    val location: String = "",
    val condition: ItemCondition = ItemCondition.NEW,
    val categories: List<Category> = emptyList(),
    val listedOn: Date = Date()
)

val fakeListings = List(30) { i ->
    ListingModel(id = (12345 + i).toString(),
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
