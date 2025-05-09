package ie.setu.getit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.getit.data.ListingModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDAO {

    @Query("SELECT * FROM listingmodel WHERE id=:id")
    fun get(id: Int): Flow<ListingModel>

    @Query("SELECT * FROM listingmodel")
    fun getAll(): Flow<List<ListingModel>>

    @Insert
    suspend fun insert(listing: ListingModel)

    @Update
    suspend fun update(listing: ListingModel)

    @Delete
    suspend fun delete(listing: ListingModel)
}