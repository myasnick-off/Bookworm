package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.network.RemoteDataSource
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSourceImpl
import com.dev.miasnikoff.bookworm.core.network.model.VolumeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenterImpl(private val dataSource: RemoteDataSource = RemoteDataSourceImpl()) :
    ListPresenter {

    private var view: ListView? = null

    override fun attachView(view: ListView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getVolumeList(query: String) {
        dataSource.getVolumeList(query = query, startIndex = 0, maxResults = 20, object :Callback<VolumeResponse> {
            override fun onResponse(
                call: Call<VolumeResponse>,
                response: Response<VolumeResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    view?.showList(body.volumes)
                } else {
                    view?.showError("Data failure!")
                }
            }

            override fun onFailure(call: Call<VolumeResponse>, t: Throwable) {
                view?.showError(t.message ?: "Unknown error!")
            }
        })
    }
}