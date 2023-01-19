package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.domain.model.BookDetails
import com.dev.miasnikoff.bookworm.ui.details.mapper.VolumeDetailsMapper

class DetailsInteractor(
    private val remoteRepository: Repository = RepositoryImpl(),
    private val localRepository: LocalRepository = LocalRepositoryImpl(),
    private val mapper: VolumeDetailsMapper = VolumeDetailsMapper(),
) {
    suspend fun getDetails(bookId: String): BookDetails {
        val detailsEntity = localRepository.getBook(bookId)
        var inFavorite = false
        if (detailsEntity != null) {
            if (detailsEntity.inHistory) {
                return mapper.fromEntity(detailsEntity)
            }
            inFavorite = detailsEntity.inFavorite
        }
        val details = mapper.fromDto(remoteRepository.getVolume(bookId).copy(isFavorite = inFavorite))
        localRepository.saveBook(mapper.toEntity(details))
        return details
    }
}