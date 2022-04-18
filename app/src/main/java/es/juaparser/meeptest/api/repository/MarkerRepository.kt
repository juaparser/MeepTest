package es.juaparser.meeptest.api.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import es.juaparser.meeptest.api.MarkerResponse
import es.juaparser.meeptest.api.MeepService

/**
 * Repositorio para gestionar los datos que se reciben de la API para enviarlos al viewmodel.
 */

class MarkerRepository {

    private val markerList = MutableLiveData<MutableList<MarkerResponse>>(mutableListOf())

    fun getMarkers(lowerLeftLocation: LatLng, upperRightLocation: LatLng): MutableLiveData<MutableList<MarkerResponse>> {
        MeepService().getMarkers(lowerLeftLocation, upperRightLocation) { markers ->
            markerList.value = markers.toMutableList()
        }
        return  markerList
    }

}