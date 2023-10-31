package com.surodeevartem.imageviewer.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.surodeevartem.imageviewer.data.paging.ImagesPagingSource
import com.surodeevartem.imageviewer.data.remote.ImagesService
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.utils.NetworkConnectionObserver
import com.surodeevartem.imageviewer.utils.NoInternetException
import com.surodeevartem.imageviewer.utils.Result
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val imagesService: ImagesService,
    private val networkConnectionObserver: NetworkConnectionObserver,
) {

    fun getNewImagesPagingData(
        sortingOrder: SortingOrder,
        sortingField: SortingField,
    ) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        initialKey = 0,
        pagingSourceFactory = {
            ImagesPagingSource(
                sortingOrder = sortingOrder,
                sortingField = sortingField,
                loadNextPage = { nextItemIndex, pageSize, sortingOrder, sortingField ->
                    if (networkConnectionObserver.hasConnection.value) {
                        try {
                            Result.Success(
                                imagesService.pagedImages(
                                    startIndex = nextItemIndex,
                                    pageSize = pageSize,
                                    sortBy = sortingField.fieldName,
                                    sortOrder = sortingOrder.type,
                                )
                            )
                        } catch (e: Exception) {
                            Result.Failure(e)
                        }
                    } else {
                        Result.Failure(NoInternetException())
                    }
                }
            )
        }
    ).flow

    companion object {
        private const val PAGE_SIZE = 20
    }
}
