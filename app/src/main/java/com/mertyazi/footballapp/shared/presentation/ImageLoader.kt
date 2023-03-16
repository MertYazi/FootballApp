package com.mertyazi.footballapp.shared.presentation

import android.widget.ImageView
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.mertyazi.footballapp.R

interface ImageLoader {
    fun ImageView.loadImage(path: String)
}

object CoilImageLoader: ImageLoader {

    override fun ImageView.loadImage(path: String) {
        val imageLoader = coil.ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadImage.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .placeholder(R.drawable.ball)
            .error(R.drawable.ball)
            .data(path)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

}