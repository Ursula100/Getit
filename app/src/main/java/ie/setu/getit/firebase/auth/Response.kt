package ie.setu.getit.firebase.auth

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val result: T): Response<T>()
    data class Failure(val e: Exception): Response<Nothing>()
}