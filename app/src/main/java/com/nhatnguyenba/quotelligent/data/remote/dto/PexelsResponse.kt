package com.nhatnguyenba.quotelligent.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PexelsResponse(
    val page: Long,
    @SerializedName("per_page")
    val perPage: Long,
    val photos: List<Photo>,
    @SerializedName("total_results")
    val totalResults: Long,
    @SerializedName("next_page")
    val nextPage: String,
)

data class Photo(
    val id: Long,
    val width: Long,
    val height: Long,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("photographer_id")
    val photographerId: Long,
    @SerializedName("avg_color")
    val avgColor: String,
    val src: Src,
    val liked: Boolean,
    val alt: String,
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)
