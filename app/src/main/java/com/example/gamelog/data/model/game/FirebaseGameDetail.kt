package com.example.gamelog.data.model.game

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseGameDetail(
    val id: Int? = null,
    val name: String? = null,
    val backgroundImage: String? = null,
    val released: String? = null,
    val rating: Double? = null,
    val metacritic: Int? = null,
    val status: Int? = null,
    val descriptionRaw: String? = null,
    val genres: List<FirebaseGenre>? = null,
    val platforms: List<FirebasePlatformWrapper>? = null,
    val publishers: List<FirebasePublisher>? = null
)

@IgnoreExtraProperties
data class FirebaseGenre(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)

@IgnoreExtraProperties
data class FirebasePlatformWrapper(
    val platform: FirebasePlatform? = null
)

@IgnoreExtraProperties
data class FirebasePlatform(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)

@IgnoreExtraProperties
data class FirebasePublisher(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)