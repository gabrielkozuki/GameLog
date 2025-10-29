import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val slug: String? = null,
    val name: String? = null,
    val playtime: Int? = null,
    val platforms: List<PlatformContainer>? = null,
    val stores: List<StoreContainer>? = null,
    val released: String? = null,
    val tba: Boolean? = null,
    @SerializedName("background_image") val backgroundImage: String? = null,
    val rating: Double? = null,
    @SerializedName("rating_top") val ratingTop: Int? = null,
    val ratings: List<Rating>? = null,
    @SerializedName("ratings_count") val ratingsCount: Int? = null,
    @SerializedName("reviews_text_count") val reviewsTextCount: Int? = null,
    val added: Int? = null,
    @SerializedName("added_by_status") val addedByStatus: AddedByStatus? = null,
    val metacritic: Int? = null,
    @SerializedName("suggestions_count") val suggestionsCount: Int? = null,
    val updated: String? = null,
    val id: Int? = null,
    val score: String? = null,
    val clip: String? = null,
    val tags: List<Tag>? = null,
    @SerializedName("esrb_rating") val esrbRating: EsrbRating? = null,
    @SerializedName("user_game") val userGame: String? = null,
    @SerializedName("reviews_count") val reviewsCount: Int? = null,
    @SerializedName("saturated_color") val saturatedColor: String? = null,
    @SerializedName("dominant_color") val dominantColor: String? = null,
    @SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshot>? = null,
    @SerializedName("parent_platforms") val parentPlatforms: List<PlatformContainer>? = null,
    val genres: List<Genre>? = null
)

@Serializable
data class PlatformContainer(
    val platform: Platform? = null
)

@Serializable
data class Platform(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class StoreContainer(
    val store: Store? = null
)

@Serializable
data class Store(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class Rating(
    val id: Int? = null,
    val title: String? = null,
    val count: Int? = null,
    val percent: Double? = null
)

@Serializable
data class AddedByStatus(
    val yet: Int? = null,
    val owned: Int? = null,
    val beaten: Int? = null,
    val toplay: Int? = null,
    val dropped: Int? = null,
    val playing: Int? = null
)

@Serializable
data class Tag(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val language: String? = null,
    @SerializedName("games_count") val gamesCount: Int? = null,
    @SerializedName("image_background") val imageBackground: String? = null
)

@Serializable
data class EsrbRating(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    @SerializedName("name_en") val nameEn: String? = null,
    @SerializedName("name_ru") val nameRu: String? = null
)

@Serializable
data class ShortScreenshot(
    val id: Int? = null,
    val image: String? = null
)

@Serializable
data class Genre(
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)