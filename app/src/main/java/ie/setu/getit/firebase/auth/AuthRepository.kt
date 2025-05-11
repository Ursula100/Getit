package ie.setu.getit.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun loginUser(email: String, password: String): Response<FirebaseUser> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Response.Success(firebaseAuth.currentUser!!)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String // not yet saved, but will soon in firestore
    ): Response<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
            )?.await()

            Response.Success(user!!)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}
