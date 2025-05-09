package ie.setu.getit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BidModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listingId: Int, // foreign key reference to Listing
    val userId: Int,
    val amount: Int,
    val status: BidStatus = BidStatus.PENDING,
    val bidTime: Date = Date()
)

enum class BidStatus {
    TOP, OUTBID, WON, LOST, PENDING
}
