package com.example.dripdrip.repository

import com.example.dripdrip.data.entities.User
import com.example.dripdrip.other.Resource
import com.example.dripdrip.other.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DefaultAuthRepository : AuthRepository {

    val auth = FirebaseAuth.getInstance()
    val users = FirebaseFirestore.getInstance().collection("users")


    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): Resource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid!!
                val user = User(uid, username)
                users.document(uid).set(user).await()
                Resource.Success(result)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }
    }

    override suspend fun reset(email: String): Resource<Void> {
        return withContext(Dispatchers.IO){
             try {
                val result = auth.sendPasswordResetEmail(email).await()
                Resource.Success(result)
            } catch(e: Exception) {
                Resource.Error("Reset link sent to email", null)
            }
        }
    }

}