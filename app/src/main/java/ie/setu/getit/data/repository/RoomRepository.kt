package ie.setu.getit.data.repository

import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.room.ListingDAO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RoomRepository @Inject
constructor(private val listingDAO: ListingDAO) {

    fun getAll(): Flow<List<ListingModel>>
            = listingDAO.getAll()

    fun get(id: Int): Flow<ListingModel>
            = listingDAO.get(id)

    suspend fun insert(listing: ListingModel)
    { listingDAO.insert(listing) }

    suspend fun update(listing: ListingModel)
    { listingDAO.update(listing) }

    suspend fun delete(listing: ListingModel)
    { listingDAO.delete(listing) }
}
