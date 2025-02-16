package org.white.green.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.firestore.firestore

class AppAuth {

    fun testIt(){

        Firebase.firestore(Firebase.app).collection("DD")


    }
}