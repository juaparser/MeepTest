package es.juaparser.meeptest.api.repository

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

import es.juaparser.meeptest.api.MarkerResponse
import es.juaparser.meeptest.api.MeepService

class BlankViewModel : ViewModel() {

    private val repository = MarkerRepository()
    var markers = MutableLiveData<MutableList<MarkerResponse>>(mutableListOf())


    fun getMarkers(lowerLeftLocation: LatLng, upperRightLocation: LatLng) {
        markers = repository.getMarkers(lowerLeftLocation, upperRightLocation)
    }
}