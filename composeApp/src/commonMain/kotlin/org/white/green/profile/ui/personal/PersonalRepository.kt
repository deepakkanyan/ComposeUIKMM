package org.white.green.profile.ui.personal

import org.white.green.firestore.network.FirestoreHelper
import org.white.green.firestore.tables.UserTables

class PersonalRepository {
    suspend fun savePreferences(preferences: PersonalModel): Result<Unit> =
        FirestoreHelper.saveToFirestore(UserTables.PREFERENCES, preferences)

    suspend fun fetchPreferences(): Result<PersonalModel> {
        return FirestoreHelper.fetchFromFirestore(UserTables.PREFERENCES)
            .mapCatching { document ->
                document.data(PersonalModel.serializer())
            }
    }

}