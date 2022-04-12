/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.juaparser.meeptest.api

import com.google.gson.annotations.SerializedName

data class MarkerResponse(
    @field:SerializedName("id") val id: Int,
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

