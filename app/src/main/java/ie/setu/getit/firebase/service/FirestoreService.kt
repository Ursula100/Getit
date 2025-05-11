package ie.setu.getit.firebase.service

import ie.setu.getit.data.model.BidModel
import ie.setu.getit.data.model.BidStatus
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.firebase.auth.Response

interface FirestoreService {

    suspend fun saveListing(listing: ListingModel): Response<Boolean>
    suspend fun updateListing(listing: ListingModel): Response<Boolean>
    suspend fun getAllListings(): Response<List<ListingModel>>
    suspend fun getListingsForUser(userId: String): Response<List<ListingModel>>
    suspend fun getListingById(id: String): Response<ListingModel>
    suspend fun deleteListing(listingId: String): Response<Boolean>

    suspend fun saveBid(bid: BidModel): Response<Boolean>
    suspend fun getBidsForListing(listingId: String): Response<List<BidModel>>
    suspend fun getBidsForUser(userId: String): Response<List<BidModel>>
    suspend fun updateBidStatus(bidId: String, newStatus: BidStatus): Response<Boolean>
}
