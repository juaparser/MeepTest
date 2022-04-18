package es.juaparser.meeptest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

import es.juaparser.meeptest.api.MarkerResponse
import es.juaparser.meeptest.api.repository.MarkerRepository

/**
 * Viewmodel para gestionar los datos del repositorio y mandarlos al fragmento.
 */

class MapViewModel : ViewModel() {

    private val repository = MarkerRepository()
    var markers = MutableLiveData<MutableList<MarkerResponse>>(mutableListOf())


    fun getMarkers(lowerLeftLocation: LatLng, upperRightLocation: LatLng) {
        markers = repository.getMarkers(lowerLeftLocation, upperRightLocation)
    }
}