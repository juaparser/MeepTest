package es.juaparser.meeptest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker


/**
 * Adapter para personalizar la ventana de los marcadores.
 *
 * Se parsea un string como HTML para adaptar el contenido a todos los datos de los marcadores.
 */


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
        if (snippet != null && snippet != "") {
            tvSnippet.text = HtmlCompat.fromHtml(snippet, 0)
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