package org.white.green.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.white.green.profile.data.ProfileModel

object ProfileAuth {

    suspend fun fetchProfileFromFirestore(): ProfileModel {
        val userID = Firebase.auth.currentUser?.uid.orEmpty()

        if (userID.isBlank()) {
            throw Exception("UserID is null")
        }
        return try {
            val document = Firebase.firestore
                .collection("Users")
                .document(userID)
                .get()

            if (document.exists) {
                ProfileModel(
                    email = document.get("email") ?: "",
                    firstName = document.get("firstName") ?: "",
                    lastName = document.get("lastName") ?: "",
                    phoneNumber = document.get("phoneNumber") ?: ""
                )
            } else {
                println("Document not found for $userID")
                ProfileModel()
            }
        } catch (e: Exception) {
            println("Error fetching profile: ${e.message}")
            throw e
        }
    }

    suspend fun saveProfile(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String
    ) {
        val userId =
            Firebase.auth.currentUser?.uid.orEmpty() // Default value if user is not authenticated
        if (userId.isBlank()) {
            throw Exception("UserID is null in saveProfile")
        }
        try {
            val userProfile = mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "email" to email,
                "phoneNumber" to phoneNumber
            )
            Firebase.firestore.collection("Users").document(userId).set(userProfile)
            println("User profile saved successfully!")
        } catch (e: Exception) {
            println("Error saving profile: ${e.message}")
        }
    }


}