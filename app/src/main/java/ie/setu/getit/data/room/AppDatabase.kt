package ie.setu.getit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.getit.data.BidModel
import ie.setu.getit.data.ListingModel

@Database(entities = [ListingModel::class, BidModel::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getListingDAO(): ListingDAO
    abstract fun getBidDAO(): BidDAO
}
