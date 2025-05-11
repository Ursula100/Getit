package ie.setu.getit.firebase.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import ie.setu.getit.data.model.BidModel
import ie.setu.getit.data.model.BidStatus
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun saveListing(listing: ListingModel): Response<Boolean> {
        return try {
            firestore.collection("listings")
                .document(listing.id.toString())
                .set(listing)
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateListing(listing: ListingModel): Response<Boolean> {
        return saveListing(listing)
    }

    override suspend fun getAllListings(): Response<List<ListingModel>> {
        return try {
            val snapshot = firestore.collection("listings").get().await()
            for (doc in snapshot.documents) {
                Timber.tag("LISTING").d(doc.data.toString())
            }
            val listings = snapshot.toObjects(ListingModel::class.java)
            Timber.tag("LISTING SCREEN").d("Number of Listings: ${listings.size}")
            Response.Success(listings)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getListingsForUser(userId: String): Response<List<ListingModel>> {
        return try {
            val snapshot = firestore.collection("listings")
                .whereEqualTo("uid", userId)
                .get()
                .await()
            val listings = snapshot.toObjects(ListingModel::class.java)
            Response.Success(listings)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getListingById(id: String): Response<ListingModel> {
        return try {
            val doc = firestore.collection("listings").document(id).get().await()
            val listing = doc.toObject(ListingModel::class.java)
            if (listing != null) {
                Response.Success(listing)
            } else {
                Response.Failure(Exception("Listing not found"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteListing(listingId: String): Response<Boolean> {
        return try {
            firestore.collection("listings").document(listingId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun saveBid(bid: BidModel): Response<Boolean> {
        return try {
            firestore.collection("bids")
                .add(bid)
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getBidsForListing(listingId: String): Response<List<BidModel>> {
        return try {
            val snapshot = firestore.collection("bids")
                .whereEqualTo("listingId", listingId)
                .get()
                .await()
            val bids = snapshot.toObjects(BidModel::class.java)
            Response.Success(bids)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getBidsForUser(userId: String): Response<List<BidModel>> {
        return try {
            val snapshot = firestore.collection("bids")
                .whereEqualTo("id", userId)
                .get()
                .await()
            val bids = snapshot.toObjects(BidModel::class.java)
            Response.Success(bids)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateBidStatus(bidId: String, newStatus: BidStatus): Response<Boolean> {
        return try {
            firestore.collection("bids")
                .document(bidId)
                .update("status", newStatus.name) // Store as string
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
