package info.octera.droidstorybox.data.remote.pack_source

import info.octera.droidstorybox.data.remote.dto.NewsResponse
import info.octera.droidstorybox.data.remote.pack_source.dto.RemotePackDto
import retrofit2.http.GET

interface PackSourceApi {
    @GET("")
    suspend fun get(): List<RemotePackDto>
}