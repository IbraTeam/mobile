package com.ibra.keytrackerapp.create_request.data

import com.ibra.keytrackerapp.create_request.domain.model.CreateRequestDto
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey
import com.ibra.keytrackerapp.create_request.domain.repository.CreateRequestRepository

class CreateRequestRepositoryImpl(
    private val createRequestApiService: CreateRequestApiService
) : CreateRequestRepository
{
    override suspend fun createRequest(token: String, request: CreateRequestDto) {
        return createRequestApiService.createRequest(token, request)
    }

    override suspend fun getFreeKeys(token: String, bookingTime: String, pairNumber : Int, repeatedCount: Int) : List<FreeKey> {
        return createRequestApiService.getFreeKeys(token, bookingTime, pairNumber, repeatedCount)
    }
}