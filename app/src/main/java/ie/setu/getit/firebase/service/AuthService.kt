package ie.setu.getit.firebase.service

import com.google.firebase.auth.FirebaseUser
import ie.setu.getit.firebase.auth.Response

interface AuthService {
    suspend fun login(email: String, password: String): Response<Boolean>
    suspend fun registerWithEmailPassword(email: String, password: String, displayName: String): Response<Boolean>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun getCurrentUserId(): String? = getCurrentUser()?.uid
}
