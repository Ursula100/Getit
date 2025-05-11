package ie.setu.getit.data.room

import androidx.room.*
import ie.setu.getit.data.model.BidModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BidDAO {
    @Query("SELECT * FROM BidModel WHERE listingId = :listingId ORDER BY amount DESC")
    fun getBidsForListing(listingId: Int): Flow<List<BidModel>>

    @Query("SELECT * FROM bidmodel WHERE userId = :userId")
    fun getBidsForUser(userId: Int): Flow<List<BidModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bid: BidModel)

    @Update
    suspend fun update(bid: BidModel)

    @Delete
    suspend fun delete(bid: BidModel)
}