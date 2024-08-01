package info.octera.droidstorybox.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface BasicHttpSource {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl:String): Response<ResponseBody>

    @GET
    suspend fun get(@Url resourceUrl: String) : Response<ResponseBody>
}