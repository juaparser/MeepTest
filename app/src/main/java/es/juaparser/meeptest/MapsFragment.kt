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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {

    private val viewModel: BlankViewModel by viewModels()
    val lowerLeft = LatLng(38.711046,-9.160096)
    val upperRight = LatLng(38.739429,-9.137115)
    lateinit var map: GoogleMap
    lateinit var mapFragment: SupportMapFragment
    private val colorMap = mutableMapOf<Int,Float>()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        map.setOnMapLoadedCallback {
            val boundsBuilder = LatLngBounds.builder()
            boundsBuilder.include(lowerLeft)
            boundsBuilder.include(upperRight)
            val bounds = boundsBuilder.build()

            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }

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

        map.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!

        mapFragment.onCreate(null)

        mapFragment.getMapAsync(callback)

        viewModel.markers.observe(requireActivity()) {

                for(m in it) {

                    val color = if(!colorMap.containsKey(m.companyZoneId)) {
                                    val random = Random().nextInt(359)
                                    colorMap[m.companyZoneId] = random.toFloat()
                                    random.toFloat()
                                } else {
                                    colorMap[m.companyZoneId]
                                }

                    map.addMarker(MarkerOptions()
                        .position(LatLng(m.y.toDouble(), m.x.toDouble()))
                        .title(m.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(color!!)))
                }

        }


    }

}