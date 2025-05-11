package ie.setu.getit.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    val currentUser get() = firebaseAuth.currentUser

    suspend fun login(email: String, password: String): Response<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun register(email: String, password: String): Response<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}