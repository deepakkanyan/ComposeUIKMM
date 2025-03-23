package org.white.green.match.repo

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.serialization.Serializable
import org.white.green.firestore.tables.UserTables
import org.white.green.profile.ui.basicProfile.BasicProfileModel
import org.white.green.profile.ui.family.FamilyInfoModel
import org.white.green.profile.ui.personal.PersonalModel

class MatchRepository {

    private val firestore = Firebase.firestore
    private var lastDocument: DocumentSnapshot? = null // Track last fetched document

    suspend fun fetchProfiles(limit: Int = 1): Result<List<CompleteProfile>> {
        return try {
            // Step 1: Create the Firestore query with pagination
            val query = firestore.collection(UserTables.BASIC_PROFILE)
                .limit(limit.toLong())

            // Step 2: Apply `startAfter()` if this is not the first page
            val paginatedQuery = lastDocument?.let { query.startAfter(it) } ?: query

            val userProfileSnapshots = paginatedQuery.get()

            // Step 3: Update lastDocument for next pagination call
            lastDocument = userProfileSnapshots.documents.lastOrNull()

            val userIds = userProfileSnapshots.documents.map { it.id }

            if (userIds.isEmpty()) {
                return Result.success(emptyList()) // No profiles found
            }

            // Step 4: Fetch complete profiles
            val profiles = userIds.mapNotNull { userId ->
              fetchCompleteProfile(userId).getOrNull() // ✅ Only fetch if it's not the logged-in user
            }

            println("✅ Successfully fetched ${profiles.size} profiles")
            Result.success(profiles)

        } catch (e: Exception) {
            println("❌ Error fetching profiles: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Fetch a complete profile (UserProfile + Family + Bio) for a given user.
     */
    private suspend fun fetchCompleteProfile(userId: String): Result<CompleteProfile> {
        return try {
            val userProfile = fetchFromFirestore<BasicProfileModel>(UserTables.BASIC_PROFILE, userId).getOrNull()
            val family = fetchFromFirestore<FamilyInfoModel>(UserTables.FAMILY, userId).getOrNull()
            val bio = fetchFromFirestore<PersonalModel>(UserTables.PREFERENCES, userId).getOrNull()

            if (userProfile == null || family == null || bio == null) {
                return Result.failure(Exception("❌ Incomplete profile data for user: $userId"))
            }

            val completeProfile = CompleteProfile(userProfile, family, bio)

            println("✅ Complete profile found for $userId: $completeProfile")
            Result.success(completeProfile)

        } catch (e: Exception) {
            println("❌ Error fetching profile for $userId: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Fetch a document from Firestore and return it as a Map.
     */
    private suspend inline fun <reified T> fetchFromFirestore(collection: String, userId: String): Result<T> {
        return try {
            val document = firestore.collection(collection).document(userId).get()

            if (document.exists) {
                val data = document.data<T>() // ✅ Deserialize using generic type
                println("✅ Successfully fetched from $collection for user: $userId")
                Result.success(data)
            } else {
                println("❌ $collection document not found for user: $userId")
                Result.failure(Exception("$collection document not found"))
            }
        } catch (e: Exception) {
            println("❌ Error fetching $collection for user $userId: ${e.message}")
            Result.failure(e)
        }
    }
}

@Serializable
data class CompleteProfile(
    val userProfile: BasicProfileModel,
    val family: FamilyInfoModel,
    val bio: PersonalModel
)
