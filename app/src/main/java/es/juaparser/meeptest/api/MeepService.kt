package es.juaparser.meeptest.api

import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_API_URL = "https://apidev.meep.me/tripplan/api/v1/"

/**
 * Clase donde se gestionan las peticiones a la API.
 * La constante BASE_API_URL es la url básica de la aplicación, donde el resto de peticiones
 * se construirán en base a esta.
 *
 * Se ha utilizado Retrofit con Gson para hacer las peticiones y construir las respuestas.
 */
class MeepService {

    //https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources?lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-9.137115

    var gson = GsonBuilder()
        .create()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Interfaz donde se han construido las peticiones.
     */
    interface MyApiEndpointInterface {

        @GET("routers/lisboa/resources")
        fun getMarkers(
            @Query("lowerLeftLatLon") lowerLeftLocation: String,
            @Query("upperRightLatLon") upperRightLatLon: String,
        ): Call<List<MarkerResponse>>

    }

    var meepService = retrofit.create(MyApiEndpointInterface::class.java)

    fun getMarkers(lowerLeftLocation: LatLng, upperRightLatLon: LatLng, res: (markers: List<MarkerResponse>) -> Unit) {
        val lowerLeft = "" + lowerLeftLocation.latitude + "," + lowerLeftLocation.longitude
        val upperRight = "" + upperRightLatLon.latitude + "," + upperRightLatLon.longitude
        val call: Call<List<MarkerResponse>> = meepService.getMarkers(lowerLeft, upperRight)
        call.enqueue(object : Callback<List<MarkerResponse>> {
            override fun onResponse(call: Call<List<MarkerResponse>>, response: Response<List<MarkerResponse>>) {
                val data = response.body()
                if (data != null) {
                    res(data)
                }
            }

            override fun onFailure(call: Call<List<MarkerResponse>>, t: Throwable?) {
                // Log error here since request failed
                t?.printStackTrace()
            }
        })
    }

}
