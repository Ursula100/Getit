package ie.setu.getit.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.setu.getit.firebase.auth.AuthRepository
import ie.setu.getit.firebase.database.FirestoreRepository
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.firebase.service.FirestoreService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthService =
        AuthRepository(firebaseAuth = auth)

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirestoreRepository(firestore: FirebaseFirestore): FirestoreService =
        FirestoreRepository(firestore)
}