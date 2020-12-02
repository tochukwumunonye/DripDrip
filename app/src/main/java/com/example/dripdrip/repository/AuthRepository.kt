package com.example.dripdrip.repository

import com.example.dripdrip.other.Resource
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun register(email: String, username:String, password: String): Resource<AuthResult>

    suspend fun login(email: String, password: String): Resource<AuthResult>

    suspend fun reset(email:String): Resource<Void>
}