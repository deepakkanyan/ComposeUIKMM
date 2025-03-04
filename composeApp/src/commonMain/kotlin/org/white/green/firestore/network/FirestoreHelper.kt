package org.white.green.firestore.network

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore

object FirestoreHelper {

    suspend inline fun <reified T : Any> saveToFirestore(
        collection: String, data: T
    ): Result<Unit> {
        val userID =
            Firebase.auth.currentUser?.uid
                ?: return Result.failure(Exception("User not logged in while $collection"))

        return try {
            Firebase.firestore.collection(collection).document(userID).set(data)
            Result.success(Unit) // ✅ Explicitly return Result.success
        } catch (e: Exception) {
            println(e.message)
            Result.failure(e) // ✅ Explicitly return Result.failure
        }
    }

    /**
     * Fetch data from Firestore and deserialize into the given type.
     */
    suspend inline fun fetchFromFirestore(collection: String): Result<DocumentSnapshot> {
        Firebase.firestore
        val userID =
            Firebase.auth.currentUser?.uid
                ?: return Result.failure(Exception("User not logged in while fetch $collection"))

        return try {
            val document = Firebase.firestore.collection(collection).document(userID).get()
            if (document.exists) {
                Result.success(document) // ✅ Explicitly return Result.success
            } else {
                Result.failure(Exception("Document not found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
