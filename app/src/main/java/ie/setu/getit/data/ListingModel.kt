package ie.setu.getit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ListingModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val location: String = "",
    val listedOn: Date = Date()
)

val fakeListings = List(30) { i ->
    ListingModel(id = 12345 + i,
        "Item $i",
        "N/A",
        i,
        "Eire",
        Date()
    )
}
