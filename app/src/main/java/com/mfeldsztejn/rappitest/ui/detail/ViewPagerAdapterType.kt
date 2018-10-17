package com.mfeldsztejn.rappitest.ui.detail

data class ViewPagerAdapterType(val type: String, val url: String) {
    companion object {
        const val IMAGE = "image"
        const val VIDEO = "video"
    }
}