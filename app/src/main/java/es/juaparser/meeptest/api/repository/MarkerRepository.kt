package es.juaparser.meeptest.api.repository

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import es.juaparser.meeptest.api.MarkerResponse
import es.juaparser.meeptest.api.MeepService

class MarkerRepository {

    private val markerList = MutableLiveData<MutableList<MarkerResponse>>(mutableListOf())

    fun getMarkers(lowerLeftLocation: LatLng, upperRightLocation: LatLng): MutableLiveData<MutableList<MarkerResponse>> {

        Log.d("JPS", "GET MARKERS REPOSITORY")
        MeepService().getMarkers(lowerLeftLocation, upperRightLocation) { markers ->
            markerList.value = markers.toMutableList()
        }
        return  markerList
    }

}