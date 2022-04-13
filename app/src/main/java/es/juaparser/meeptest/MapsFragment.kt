package es.juaparser.meeptest

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val viewModel: BlankViewModel by viewModels()
    val lowerLeft = LatLng(38.711046,-9.160096)
    val upperRight = LatLng(38.739429,-9.137115)
    lateinit var map: GoogleMap
    lateinit var mapFragment: SupportMapFragment

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

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
        viewModel.getMarkers(lowerLeft, upperRight)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    fun handleMap() {

        Log.d("JPS", "HANDLE MAP ")
        map.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))

        map.addMarker(MarkerOptions().position(upperRight).title("Marker upper right"))
        map.addMarker(MarkerOptions().position(lowerLeft).title("Marker lower left"))

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.center, 15f))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20))


        val boundsBuilder = LatLngBounds.builder()
        boundsBuilder.include(lowerLeft)
        boundsBuilder.include(upperRight)
        val bounds = boundsBuilder.build()

        val newLatLngBounds = CameraUpdateFactory.newLatLngZoom(lowerLeft, 14f)
        map.moveCamera(newLatLngBounds)

/*        val visibleRegion = map.projection.visibleRegion
        val mapLatLngBound = visibleRegion.latLngBounds

        map.moveCamera(CameraUpdateFactory.newLatLng(mapLatLngBound.center))*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!

        mapFragment.onCreate(null)

        mapFragment.getMapAsync(callback)

        viewModel.markers.observe(requireActivity()) {
            Log.d("JPS", "MARKERS OBTENIDOS: " + it)/*
            val bounds = LatLngBounds(
                lowerLeft,  // SW bounds
                upperRight // NE bounds
            )*/

            if(::map.isInitialized) {
                Log.d("JPS", "MAP INITIALIZED OBSERVER")
                for(m in it) {
                    map.addMarker(MarkerOptions()
                        .position(LatLng(m.y.toDouble(), m.x.toDouble()))
                        .title(m.name))
                }
            } else {
                Log.d("JPS", "MAP INITIALIZED OBSERVER")
            }

        }


    }
}