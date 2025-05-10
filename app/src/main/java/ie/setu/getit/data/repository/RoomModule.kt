package ie.setu.getit.data.repository

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.getit.data.room.AppDatabase
import ie.setu.getit.data.room.BidDAO
import ie.setu.getit.data.room.ListingDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):
            AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "listing_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideListingDAO(appDatabase: AppDatabase):
            ListingDAO = appDatabase.getListingDAO()

    @Provides
    fun provideBidDAO(appDatabase: AppDatabase):
            BidDAO = appDatabase.getBidDAO()


    @Provides
    fun provideRoomRepository(listingDAO: ListingDAO, bidDAO: BidDAO):
            RoomRepository = RoomRepository(listingDAO, bidDAO)
}
