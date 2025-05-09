package ie.setu.getit.data.repository

import ie.setu.getit.data.BidModel
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.room.BidDAO
import ie.setu.getit.data.room.ListingDAO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RoomRepository @Inject
constructor(
    private val listingDAO: ListingDAO,
    private val bidDAO: BidDAO
) {
    //Listings
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

    //Bids
    fun getBidsForListing(listingId: Int): Flow<List<BidModel>> = bidDAO.getBidsForListing(listingId)

    suspend fun insertBid(bid: BidModel) { bidDAO.insert(bid) }

    suspend fun updateBid(bid: BidModel) { bidDAO.update(bid) }

    suspend fun deleteBid(bid: BidModel) { bidDAO.delete(bid) }
}


