package org.white.green.feature.profile.ui.basicProfile


import org.white.green.firestore.network.FirestoreHelper
import org.white.green.firestore.tables.UserTables

class BasicProfileRepository {
    suspend fun save(model: BasicProfileModel): Result<Unit> =
        FirestoreHelper.saveToFirestore(UserTables.BASIC_PROFILE, model)

    suspend fun fetch(): Result<BasicProfileModel> {
        return FirestoreHelper.fetchFromFirestore(UserTables.BASIC_PROFILE)
            .mapCatching { document ->
                document.data(BasicProfileModel.serializer())
            }
    }
}
