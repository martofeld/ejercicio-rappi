package com.mfeldsztejn.rappitest.dtos

data class Configuration(val images: Images)

data class Images(val secureBaseUrl: String, val posterSizes: List<String>, val backdropSizes: List<String>) {
    fun backdropUrl(endpoint: String, screenSize: Int): String {
        return secureBaseUrl + findBestSize(backdropSizes, screenSize) + endpoint
    }

    fun posterUrl(endpoint: String, screenSize: Int): String {
        return secureBaseUrl + findBestSize(posterSizes, screenSize) + endpoint
    }

    private fun findBestSize(sizes: List<String>, screenSize: Int): String {
        var position = -(posterSizes
                .filter { it.contains(Regex("[0-9]+")) }
                .map { it.removePrefix("w").toInt() }
                .binarySearch(screenSize) + 1) // Add 1 to get the next bigger
        if (position > sizes.size)
            position = sizes.size - 1
        return sizes[position]
    }
}