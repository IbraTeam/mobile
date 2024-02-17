package com.ibra.keytrackerapp.common.keytrack.domain.usecase

import com.ibra.keytrackerapp.common.keytrack.domain.repository.KeyTrackRepository
import javax.inject.Inject

class KeyTrackUseCases @Inject constructor(
    private val keyTrackRepository: KeyTrackRepository
) {
    suspend fun getKeys(token: String) = keyTrackRepository.getKeys(token)
    suspend fun giveKey(token: String, userId: String, keyId: String) = keyTrackRepository.giveKey(token, userId, keyId)
    suspend fun getKey(token: String, keyId: String) = keyTrackRepository.getKey(token, keyId)
    suspend fun returnKey(token: String, keyId: String) = keyTrackRepository.returnKey(token, keyId)
    suspend fun rejectKey(token: String, keyId: String) = keyTrackRepository.rejectKey(token, keyId)
    suspend fun acceptKey(token: String, keyId: String) = keyTrackRepository.acceptKey(token, keyId)
    suspend fun cancelKey(token: String, keyId: String) = keyTrackRepository.cancelKey(token, keyId)
}