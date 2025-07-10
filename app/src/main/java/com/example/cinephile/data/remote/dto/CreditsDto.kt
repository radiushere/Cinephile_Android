package com.example.cinephile.data.remote.dto

import com.google.gson.annotations.SerializedName

// This DTO is for the movie/show cast
data class CreditsDto(
    val cast: List<CastMemberDto>
)

data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?
)