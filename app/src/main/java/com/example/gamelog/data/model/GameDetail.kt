package com.example.gamelog.data.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class GameDetail(
    val id: Int? = null,
    val slug: String? = null,
    val name: String? = null,
    @SerializedName("name_original")
    val nameOriginal: String? = null,
    val description: String? = null,
    @SerializedName("description_raw")
    val descriptionRaw: String? = null,
    val metacritic: Int? = null,
    @SerializedName("metacritic_platforms")
    val metacriticPlatforms: List<MetacriticPlatform>? = null,
    val released: String? = null,
    val tba: Boolean? = null,
    val updated: String? = null,
    @SerializedName("background_image")
    val backgroundImage: String? = null,
    @SerializedName("background_image_additional")
    val backgroundImageAdditional: String? = null,
    val website: String? = null,
    val rating: Double? = null,
    @SerializedName("rating_top")
    val ratingTop: Int? = null,
    val ratings: List<Rating>? = null,
    val reactions: Map<String, Int>? = null,
    val added: Int? = null,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatus? = null,
    val playtime: Int? = null,
    @SerializedName("screenshots_count")
    val screenshotsCount: Int? = null,
    @SerializedName("movies_count")
    val moviesCount: Int? = null,
    @SerializedName("creators_count")
    val creatorsCount: Int? = null,
    @SerializedName("achievements_count")
    val achievementsCount: Int? = null,
    @SerializedName("parent_achievements_count")
    val parentAchievementsCount: Int? = null,
    @SerializedName("reddit_url")
    val redditUrl: String? = null,
    @SerializedName("reddit_name")
    val redditName: String? = null,
    @SerializedName("reddit_description")
    val redditDescription: String? = null,
    @SerializedName("reddit_logo")
    val redditLogo: String? = null,
    @SerializedName("reddit_count")
    val redditCount: Int? = null,
    @SerializedName("twitch_count")
    val twitchCount: Int? = null,
    @SerializedName("youtube_count")
    val youtubeCount: Int? = null,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int? = null,
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int? = null,
    @SerializedName("alternative_names")
    val alternativeNames: List<String>? = null,
    @SerializedName("metacritic_url")
    val metacriticUrl: String? = null,
    @SerializedName("parents_count")
    val parentsCount: Int? = null,
    @SerializedName("additions_count")
    val additionsCount: Int? = null,
    @SerializedName("game_series_count")
    val gameSeriesCount: Int? = null,
    @SerializedName("user_game")
    val userGame: Any? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
    @SerializedName("saturated_color")
    val saturatedColor: String? = null,
    @SerializedName("dominant_color")
    val dominantColor: String? = null,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatform>? = null,
    val platforms: List<PlatformWrapper>? = null,
    val stores: List<StoreWrapper>? = null,
    val developers: List<Developer>? = null,
    val genres: List<Genre>? = null,
    val tags: List<Tag>? = null,
    val publishers: List<Publisher>? = null,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRating? = null,
    val clip: Any? = null,
    val status: Int? = null
)

@IgnoreExtraProperties
data class MetacriticPlatform(
    val metascore: Int? = null,
    val url: String? = null,
    val platform: PlatformSimple? = null
)

@IgnoreExtraProperties
data class PlatformSimple(
    val platform: Int? = null,
    val name: String? = null,
    val slug: String? = null
)

@IgnoreExtraProperties
data class Rating(
    val id: Int? = null,
    val title: String? = null,
    val count: Int? = null,
    val percent: Double? = null
)

@IgnoreExtraProperties
data class AddedByStatus(
    val yet: Int? = null,
    val owned: Int? = null,
    val beaten: Int? = null,
    val toplay: Int? = null,
    val dropped: Int? = null,
    val playing: Int? = null
)

@IgnoreExtraProperties
data class ParentPlatform(
    val platform: PlatformSimple? = null
)

@IgnoreExtraProperties
data class PlatformWrapper(
    val platform: PlatformDetail? = null,
    @SerializedName("released_at")
    val releasedAt: String? = null,
    val requirements: Requirements? = null
)

@IgnoreExtraProperties
data class PlatformDetail(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val image: String? = null,
    @SerializedName("year_end")
    val yearEnd: Int? = null,
    @SerializedName("year_start")
    val yearStart: Int? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class Requirements(
    val minimum: String? = null,
    val recommended: String? = null
)

@IgnoreExtraProperties
data class StoreWrapper(
    val id: Int? = null,
    val url: String? = null,
    val store: Store? = null
)

@IgnoreExtraProperties
data class Store(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val domain: String? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class Developer(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class Genre(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class Tag(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val language: String? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class Publisher(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null
)

@IgnoreExtraProperties
data class EsrbRating(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)


fun GameDetail.toFirebase(): FirebaseGameDetail {
    return FirebaseGameDetail(
        id = this.id,
        name = this.name,
        backgroundImage = this.backgroundImage,
        released = this.released,
        rating = this.rating,
        metacritic = this.metacritic,
        status = this.status,
        descriptionRaw = this.descriptionRaw,
        genres = this.genres?.map { it.toFirebase() },
        platforms = this.platforms?.map { it.toFirebase() },
        publishers = this.publishers?.map { it.toFirebase() }
    )
}

fun FirebaseGameDetail.toDomain(): GameDetail {
    return GameDetail(
        id = this.id,
        name = this.name,
        backgroundImage = this.backgroundImage,
        released = this.released,
        rating = this.rating,
        metacritic = this.metacritic,
        status = this.status,
        descriptionRaw = this.descriptionRaw,
        genres = this.genres?.map { it.toDomain() },
        platforms = this.platforms?.map { it.toDomain() },
        publishers = this.publishers?.map { it.toDomain() }
    )
}

private fun Genre.toFirebase() = FirebaseGenre(id, name, slug)
private fun FirebaseGenre.toDomain() = Genre(id, name, slug)

private fun PlatformWrapper.toFirebase() = FirebasePlatformWrapper(
    platform = platform?.let { FirebasePlatform(it.id, it.name, it.slug) }
)
private fun FirebasePlatformWrapper.toDomain() = PlatformWrapper(
    platform = platform?.let { PlatformDetail(it.id, it.name, it.slug) }
)

private fun Publisher.toFirebase() = FirebasePublisher(id, name, slug)
private fun FirebasePublisher.toDomain() = Publisher(id, name, slug)