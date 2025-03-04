package org.white.green.profile.ui.preferences

import org.white.green.firestore.network.FirestoreHelper
import org.white.green.firestore.tables.UserTables

class PreferencesRepository {
    suspend fun savePreferences(preferences: PreferencesModel): Result<Unit> =
        FirestoreHelper.saveToFirestore(UserTables.PREFERENCES, preferences)

    suspend fun fetchPreferences(): Result<PreferencesModel> {
        return FirestoreHelper.fetchFromFirestore(UserTables.PREFERENCES)
            .mapCatching { document ->
                document.data(PreferencesModel.serializer())
            }
    }

}