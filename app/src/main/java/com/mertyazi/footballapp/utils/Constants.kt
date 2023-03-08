package com.mertyazi.footballapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.application.MatchesApplication

object Constants {
    const val BASE_URL: String = "https://api.football-data.org"
    const val TIMED: String = "TIMED"
    const val TODAY: String = "TODAY"
    const val PAUSED: String = "PAUSED"
    const val FINISHED: String = "FINISHED"
    const val POSTPONED: String = "POSTPONED"
    const val SCHEDULED: String = "SCHEDULED"
    const val FIRST_HALF: String = "FIRST HALF"
    const val IN_PLAY: String = "IN PLAY"
    const val IN_PLAY_API: String = "IN_PLAY"
    const val ALL_LEAGUES: String = "All Leagues"
    const val NO_MATCHES_TODAY: String = "No Matches Today"
    const val MATCH_STATUS: String = "matchStatus"
    const val HOME_SCORE: String = "HomeScore"
    const val AWAY_SCORE: String = "AwayScore"
    const val REFRESHING_TIME: Long = 45000
    const val REFRESHING_TIME_MATCHES: Long = 30000
    const val GROUP_STAGE: String = "GROUP_STAGE"

    fun checkConnection(viewModel: AndroidViewModel): Boolean {
        val connectivityManager = viewModel.getApplication<MatchesApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun toTimeForTR(timeString: String, status: String): String {
        val date = timeString.split("T")[0]
        val timeUTC =
            timeString.substring(timeString.lastIndexOf('T') + 1, timeString.lastIndexOf(":00Z"))
        val time = timeUTC.split(":")
        val timeHour: String = when (time[0]) {
            "21" -> "00"
            "22" -> "1"
            "23" -> "2"
            "00" -> "3"
            else -> (time[0].toInt() + 3).toString()
        }
        if (status == TODAY) {
            return timeHour + ":" + time[1]
        } else {
            return date + " | " +  timeHour + ":" + time[1]
        }
    }

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            //.crossfade(true)
            //.crossfade(500)
            .placeholder(R.drawable.ball)
            .error(R.drawable.ball)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }
}
