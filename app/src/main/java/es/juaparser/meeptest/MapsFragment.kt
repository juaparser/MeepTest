package es.juaparser.meeptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import es.juaparser.meeptest.api.MarkerResponse
import java.util.*

/**
 *
 * Fragmento donde se carga el mapa y se realiza la consulta.
 *
 * Al crearse, se lanza la consulta a la API para cargar los marcadores, a la vez que mientras
 * se va cargando el mapa de forma asíncrona.
 *
 * Cuando se ha obtenido una respuesta y se carguen los marcadores en el repositorio, esta carga
 * se notificará al viewmodel y de vuelta al fragmento, donde se pintan los marcadores en el mapa.
 *
 * Para que sea escalable la diferenciación de marcadores, se les asigna un color aleatorio cada vez
 * que se inicia el fragmento agrupado por la propiedad companyZoneId. Esta diferenciación se va
 * almacenando en un mapa para ir comprobando si se van registrando correctamente.
 *
 * La asignación de colores aleatoria puede dar pie a que coincidan dos colores muy parecidos
 * y la distinción sea más dificil.
 *
 * Se añade pantalla de carga de marcadores para evitar que la experiencia de usuario sea peor.
 * Las llamadas a la API para obtener los resultados podrían optimizarse controlando cuando es
 * un movimiento de cámara del usuario y cuando por zoom.
 *
 */

class MapsFragment : Fragment() {

    private val viewModel: MapViewModel by viewModels()

    val lowerLeft = LatLng(38.711046,-9.160096)
    val upperRight = LatLng(38.739429,-9.137115)
    lateinit var map: GoogleMap
    lateinit var mapFragment: SupportMapFragment
    private val colorMap = mutableMapOf<Int,Float>()
    private val visibleMarkers = mutableListOf<Marker>()
    private lateinit var loadingLayout: Group

    private val callback = OnMapReadyCallback { googleMap ->
        loadingLayout.visibility = View.VISIBLE
        viewModel.getMarkers(lowerLeft, upperRight)

        viewModel.markers.observe(requireActivity()) {

            for(m in it) {

                val color = if(!colorMap.containsKey(m.companyZoneId)) {
                    val random = Random().nextInt(359)
                    colorMap[m.companyZoneId] = random.toFloat()
                    random.toFloat()
                } else {
                    colorMap[m.companyZoneId]
                }

                val marker = map.addMarker(MarkerOptions()
                    .position(LatLng(m.y.toDouble(), m.x.toDouble()))
                    .title(m.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(color!!))
                    .snippet(markerWindowText(m)))

                visibleMarkers.add(marker!!)
            }

            for(mark in visibleMarkers) {
                var visible = false
                for(mrk in it) {
                    if(mark.position == LatLng(mrk.y.toDouble(), mrk.x.toDouble())) {
                        visible = true
                    }
                }
                if(!visible) mark.remove()
            }

            loadingLayout.visibility = View.GONE
        }

        map = googleMap

        map.setOnMapLoadedCallback {
            val boundsBuilder = LatLngBounds.builder()
            boundsBuilder.include(lowerLeft)
            boundsBuilder.include(upperRight)
            val bounds = boundsBuilder.build()

            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }

        map.setOnCameraChangeListener(OnCameraChangeListener {
            val latLng = map.projection.visibleRegion.latLngBounds

            loadingLayout.visibility = View.VISIBLE
            viewModel.getMarkers(latLng.southwest, latLng.northeast)
        })

        if(::map.isInitialized) {
            handleMap()
        }
        mapFragment.onResume()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        loadingLayout = view.findViewById(R.id.loadingLayout)
        return view
    }

    fun handleMap() {

        map.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!

        mapFragment.onCreate(null)

        mapFragment.getMapAsync(callback)

    }

    private fun markerWindowText(markerResponse: MarkerResponse): String {
        var response = ""

        response += "<strong>Battery level: </strong>" + markerResponse.batteryLevel + "<br>"
        response += "<strong>Helmets: </strong>" + markerResponse.helmets + "<br>"
        response += "<strong>Company zone: </strong>" + markerResponse.companyZoneId + "<br>"
        response += "<strong>License plate: </strong>" + markerResponse.licencePlate + "<br>"
        response += "<strong>Model: </strong>" + markerResponse.model + "<br>"
        response += "<strong>Range: </strong>" + markerResponse.range + "<br>"
        response += "<strong>Realtime data: </strong>" + markerResponse.realTimeData + "<br>"
        response += "<strong>Resource image: </strong>" + markerResponse.resourceImageId + "<br>"
        response += "<strong>Resource image URLs: </strong>" + markerResponse.resourceImagesUrls + "<br>"
        response += "<strong>Resource type: </strong>" + markerResponse.resourceType + "<br>"

        return response
    }

}