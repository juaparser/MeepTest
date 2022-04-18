package es.juaparser.meeptest.api

import com.google.gson.annotations.SerializedName

/**
 * Modelo de la respuesta a la petición de la API para obtener los marcadores
 * y su información, creado a partir del JSON de respuesta.
 */

data class MarkerResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("x") val x: Float,
    @field:SerializedName("y") val y: Float,
    @field:SerializedName("licencePlate") val licencePlate: String,
    @field:SerializedName("range") val range: Int,
    @field:SerializedName("batteryLevel") val batteryLevel: Int,
    @field:SerializedName("helmets") val helmets: Int,
    @field:SerializedName("model") val model: String,
    @field:SerializedName("resourceImageId") val resourceImageId: String,
    @field:SerializedName("resourceImagesUrls") val resourceImagesUrls: List<String>,
    @field:SerializedName("realTimeData") val realTimeData: Boolean,
    @field:SerializedName("resourceType") val resourceType: String,
    @field:SerializedName("companyZoneId") val companyZoneId: Int,
)

