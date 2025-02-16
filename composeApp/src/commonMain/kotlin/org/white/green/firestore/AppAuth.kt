package org.white.green.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AppAuth {

    fun testIt() : String{
    return  Firebase.app.name

    }


    fun login(email : String, password : String) : Flow <String> {
       return flow {
           val emailUser =   Firebase.auth.signInWithEmailAndPassword(email, password).user?.email ?: "user not found"
           println(emailUser)
           emit(emailUser)
        }


    }
}