package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSource
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSourceImpl
import com.dev.miasnikoff.bookworm.core.network.model.VolumeResponse
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.list.adapter.VolumeItem
import com.dev.miasnikoff.bookworm.list.mapper.VolumeDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenterImpl(
    private val dataSource: RemoteDataSource = RemoteDataSourceImpl(),
    private val mapper: VolumeDataMapper = VolumeDataMapper()
) :
    ListPresenter {

    private var view: ListView? = null
    private var volumeList: List<RecyclerItem> = listOf()

    override fun attachView(view: ListView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getVolumeList(query: String) {
        dataSource.getVolumeList(
            query = query,
            startIndex = DEFAULT_START_INDEX,
            maxResults = DEFAULT_MAX_VALUES,
            object : Callback<VolumeResponse> {
                override fun onResponse(
                    call: Call<VolumeResponse>,
                    response: Response<VolumeResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        volumeList = mapper.toRecyclerItems(body.volumes)
                        view?.showList(volumeList)
                    } else {
                        view?.showError("Data failure!")
                    }
                }

                override fun onFailure(call: Call<VolumeResponse>, t: Throwable) {
                    view?.showError(t.message ?: "Unknown error!")
                }
            })
    }

    override fun setFavorite(itemId: String) {
        val newList: MutableList<RecyclerItem> = mutableListOf()
        newList.addAll(volumeList)
        val index = newList.indexOfFirst { it.id == itemId }
        val volumeItem = (newList.firstOrNull { it.id == itemId } as? VolumeItem)
        if (index > -1 && volumeItem != null) {
            newList[index] = if (volumeItem.isFavorite)
                volumeItem.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24)
            else
                volumeItem.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24)
        }
        volumeList = newList
        view?.showList(volumeList)
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
    }
}