package com.vietgurus.common.utils.view_helper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView



abstract class EndlessRecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private val previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = false // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 0// The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var current_page = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy < 0) {
            return
        }
        // check for scroll down only
        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

        // to make sure only one onLoadMore is triggered
        synchronized(this) {
            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                // End has been reached, Do something
                current_page++
                loading = true
                onLoadMore(current_page)
                //loading = true
            }
        }
    }


    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    fun reset() {
        loading = false
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        current_page = 1
    }

    abstract fun onLoadMore(current_page: Int)

    companion object {
        var TAG = EndlessRecyclerOnScrollListener::class.java.simpleName
    }
}