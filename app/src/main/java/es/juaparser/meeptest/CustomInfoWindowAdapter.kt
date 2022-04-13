package es.juaparser.meeptest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker


class CustomInfoWindowAdapter(context: Context) : InfoWindowAdapter {

    private val mWindow = LayoutInflater.from(context).inflate(R.layout.marker_window, null)

    private fun createWindow(marker: Marker, view: View) {
        val title = marker.title
        val tvTitle = view.findViewById(R.id.title) as TextView
        if (title != "") {
            tvTitle.text = title
        }
        val snippet = marker.snippet
        val tvSnippet = view.findViewById(R.id.snippet) as TextView
        if (snippet != "") {
            tvSnippet.text = snippet
        }
    }

    override fun getInfoWindow(marker: Marker): View? {
        createWindow(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View? {
        createWindow(marker, mWindow)
        return mWindow
    }
}