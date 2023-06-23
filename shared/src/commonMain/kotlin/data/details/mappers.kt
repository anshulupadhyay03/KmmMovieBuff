package data.details

import domain.*
import util.format


internal fun mapToDomain(response: MovieDetailsResponse): MovieDetailsModel {
    val title = response.title
    val overview = response.overview
    val releaseDate = response.release_date
    val genres = response.genres.joinToString {
        it.name
    }

    val duration = response.runtime
    val backDropImage = response.backdrop_path
    val vote = response.vote_average
    val posterImage = response.poster_path
    val movieInfo = MovieInfo(
        response.status,
        response.original_language,
        response.budget,
        response.revenue
    )

    val keywords = response.keywords.keywords.map {
        it.name
    }

    val reviews = mapUserReviews(response.reviews)

    val topCast = response.credits.cast.asSequence()
        .filter {
            it.known_for_department == "Acting"
        }.map {
            MovieCast(it.original_name, it.profile_path, it.character)
        }.toList()

    val videos = response.videos.results.map {
        Videos(it.key, it.site)
    }

    val posters = response.images.posters.map {
        it.file_path
    }

    val backdrops = response.images.backdrops.map {
        it.file_path
    }



    return MovieDetailsModel(
        title,
        overview,
        releaseDate,
        genres,
        duration,
        backDropImage,
        vote,
        posterImage,
        movieInfo,
        keywords,
        reviews,
        topCast,
        videos,
        posters,
        backdrops
    )
}

private fun mapUserReviews(reviews: MovieReviewsResponse): List<MovieReview> {
    return reviews.results.map {
        MovieReview(
            it.content
                .replace("\r", "")
                .replace("\n", ""),
            it.author,
            it.updated_at.dateFormat(),
            it.url,
            it.author_details.avatar_path ?: "",
            it.author_details.rating ?: 0.0
        )
    }
}

private fun String.dateFormat(): String {
    val dateFormat = "dd-MMM-yy"
    return this.format(dateFormat)

}