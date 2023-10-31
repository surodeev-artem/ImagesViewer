package com.surodeevartem.imageviewer.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.utils.Result
import com.surodeevartem.imageviewer.utils.fold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagesPagingSource(
    private val sortingOrder: SortingOrder,
    private val sortingField: SortingField,
    private val loadNextPage: suspend (
        nextItemIndex: Int,
        pageSize: Int,
        sortingOrder: SortingOrder,
        sortingField: SortingField,
    ) -> Result<List<ImageEntity>>,
) : PagingSource<Int, ImageEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ImageEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageEntity> {
        return withContext(Dispatchers.IO) {
            val nextPageNumber = params.key ?: 1
            val response = loadNextPage(
                nextPageNumber * params.loadSize,
                params.loadSize,
                sortingOrder,
                sortingField,
            )

            response.fold(
                onSuccess = { result ->
                    LoadResult.Page(
                        data = result,
                        prevKey = null,
                        nextKey = nextPageNumber + 1,
                    )
                },
                onFailure = { LoadResult.Error(it) },
            )
        }
    }

}
