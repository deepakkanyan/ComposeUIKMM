package org.white.green.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

object AppAuth {

    fun login(email: String, password: String): Flow<Result<String>> = flow {
        try {
            val userEmail = Firebase.auth.signInWithEmailAndPassword(email, password).user?.email
            if (userEmail != null) {
                emit(Result.success(userEmail))
            } else {
                emit(Result.failure(Exception("User not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun isUserLoggedIn(): Flow<Boolean> = flow {
        emit(Firebase.auth.currentUser != null)
    }.catch {
        emit(false)
    }  // If any error occurs, return false


    fun registerUser(email: String, password: String): Flow<Result<AuthResult>> {
        return flow {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password)
                emit(Result.success(result)) // Emit success if registration is successful
            } catch (e: Exception) {
                emit(Result.failure(e)) // Emit failure with exception if registration fails
            }
        }.catch { e -> emit(Result.failure(e)) } // Catch any unexpected errors
    }

    suspend fun signOut() {
        Firebase.auth.signOut()
    }
}
