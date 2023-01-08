package com.example.onlineshop.data.network

import com.example.onlineshop.util.Result
import com.example.onlineshop.util.data
import com.example.onlineshop.util.succeeded

class DefaultPagintor<Key, Item> constructor(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend () -> Key,
    private inline val onError: suspend (String?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator {
    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if(isMakingRequest) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)
        isMakingRequest = false

        if (result is Result.Error) {
            onError(result.errorMessage)
            onLoadUpdated(false)
            return
        }

        if (result.succeeded) {
            val items = result.data ?: emptyList()
            currentKey = getNextKey()
            onSuccess(items, currentKey)
            onLoadUpdated(false)
        }
    }

    override fun refreshItems() {
        currentKey = initialKey
    }
}