package com.ibra.keytrackerapp.key_requests.domain.repository

import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests

interface RequestRepository {
    suspend fun getUserRequests(token: String, weekStart: String) : UserRequests
    suspend fun deleteRequest(token: String, id: String)
}