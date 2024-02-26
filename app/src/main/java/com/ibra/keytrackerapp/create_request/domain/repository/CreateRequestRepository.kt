package com.ibra.keytrackerapp.create_request.domain.repository

import com.ibra.keytrackerapp.create_request.domain.model.CreateRequestDto
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey

interface CreateRequestRepository {
    suspend fun createRequest(token : String, request: CreateRequestDto)
    suspend fun getFreeKeys(token : String, bookingTime : String, pairNumber: Int, repeatedCount : Int) : List<FreeKey>
}