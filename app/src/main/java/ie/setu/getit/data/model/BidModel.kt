package ie.setu.getit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BidModel(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val listingId: String, // reference to Listing
    val userId: String,
    val amount: Int,
    val status: BidStatus = BidStatus.PENDING,
    val bidTime: Date = Date()
)

enum class BidStatus {
    TOP, OUTBID, WON, LOST, PENDING
}
