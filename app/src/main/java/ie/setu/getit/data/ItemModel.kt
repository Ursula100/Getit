package ie.setu.getit.data

import java.util.Date
import kotlin.random.Random

data class ItemModel(val id: Int = Random.nextInt(1, 100000),
                     val name: String = "",
                     val description: String = "",
                     val price: Int = 0,
                     val location: String = "",
                     val listedOn: Date = Date()
)

val fakeListings = List(30) { i ->
    ItemModel(id = 12345 + i,
        "Item $i",
        "N/A",
        i,
        "Eire",
        Date()
    )
}
