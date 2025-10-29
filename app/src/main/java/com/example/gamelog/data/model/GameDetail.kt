package com.example.gamelog.data.model

data class GameDetail(
    val id: Int,
    val slug: String,
    val name: String,
    val name_original: String,
    val description: String?,
    val description_raw: String?,
    val metacritic: Int?,
    val metacritic_platforms: List<MetacriticPlatform>?,
    val released: String?,
    val tba: Boolean,
    val updated: String?,
    val background_image: String?,
    val background_image_additional: String?,
    val website: String?,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Rating>,
    val reactions: Map<String, Int>?,
    val added: Int?,
    val added_by_status: AddedByStatus?,
    val playtime: Int?,
    val screenshots_count: Int?,
    val movies_count: Int?,
    val creators_count: Int?,
    val achievements_count: Int?,
    val parent_achievements_count: Int?,
    val reddit_url: String?,
    val reddit_name: String?,
    val reddit_description: String?,
    val reddit_logo: String?,
    val reddit_count: Int?,
    val twitch_count: Int?,
    val youtube_count: Int?,
    val reviews_text_count: Int?,
    val ratings_count: Int?,
    val suggestions_count: Int?,
    val alternative_names: List<String>?,
    val metacritic_url: String?,
    val parents_count: Int?,
    val additions_count: Int?,
    val game_series_count: Int?,
    val user_game: Any?,
    val reviews_count: Int?,
    val saturated_color: String?,
    val dominant_color: String?,
    val parent_platforms: List<ParentPlatform>?,
    val platforms: List<PlatformWrapper>?,
    val stores: List<StoreWrapper>?,
    val developers: List<Developer>?,
    val genres: List<Genre>?,
    val tags: List<Tag>?,
    val publishers: List<Publisher>?,
    val esrb_rating: EsrbRating?,
    val clip: Any?
)

data class MetacriticPlatform(
    val metascore: Int?,
    val url: String?,
    val platform: PlatformSimple
)

data class PlatformSimple(
    val platform: Int?,
    val name: String?,
    val slug: String?
)

data class Rating(
    val id: Int,
    val title: String,
    val count: Int,
    val percent: Double
)

data class AddedByStatus(
    val yet: Int?,
    val owned: Int?,
    val beaten: Int?,
    val toplay: Int?,
    val dropped: Int?,
    val playing: Int?
)

data class ParentPlatform(
    val platform: PlatformSimple
)

data class PlatformWrapper(
    val platform: PlatformDetail,
    val released_at: String?,
    val requirements: Requirements?
)

data class PlatformDetail(
    val id: Int?,
    val name: String?,
    val slug: String?,
    val image: String?,
    val year_end: Int?,
    val year_start: Int?,
    val games_count: Int?,
    val image_background: String?
)

data class Requirements(
    val minimum: String? = null,
    val recommended: String? = null
)

data class StoreWrapper(
    val id: Int,
    val url: String?,
    val store: Store
)

data class Store(
    val id: Int,
    val name: String,
    val slug: String,
    val domain: String?,
    val games_count: Int?,
    val image_background: String?
)

data class Developer(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int?,
    val image_background: String?
)

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int?,
    val image_background: String?
)

data class Tag(
    val id: Int,
    val name: String,
    val slug: String,
    val language: String?,
    val games_count: Int?,
    val image_background: String?
)

data class Publisher(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int?,
    val image_background: String?
)

data class EsrbRating(
    val id: Int,
    val name: String,
    val slug: String
)
