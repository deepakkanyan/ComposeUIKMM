package org.white.green.profile.ui.family


import org.white.green.firestore.network.FirestoreHelper
import org.white.green.firestore.tables.UserTables

class FamilyRepository {
    suspend fun save(model: FamilyInfoModel): Result<Unit> =
        FirestoreHelper.saveToFirestore(UserTables.FAMILY, model)

    suspend fun fetch(): Result<FamilyInfoModel> {
        return FirestoreHelper.fetchFromFirestore(UserTables.FAMILY)
            .mapCatching { document ->
                document.data(FamilyInfoModel.serializer())
            }
    }

}
