package com.siksinhot.test.server.route.twin

import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.model.PostBody
import com.siksinhot.test.server.service.twin.PlaceService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.eclipse.jetty.http.HttpStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.place(placeService: PlaceService) {

    post("/api/v1/place/callback") {
        //aop(call, false) {
            val body: PostBody = call.receive<PostBody>()

            call.respond(HttpStatus.OK_200)
        //}
    }

    get("/api/v1/place/send/{placeId}") {
        //aop(call, false) {
            val placeId = call.parameters["placeId"]?.toLong()
            val dto = placeService.getPlace(placeId!!)
            val body = PostBody(
                requestId = UUID.randomUUID().toString(),
                callbackUrl = Env.serverUrl + "/api/v1/place/callback",
                type = "place",
                data = dto as Any
            )
            val remote = Env.twinkoreaService.getInstance().send(body)
            remote.enqueue(object : Callback<PostBody> {
                override fun onResponse(
                    call: Call<PostBody>,
                    response: Response<PostBody>
                ) {
                }

                override fun onFailure(call: Call<PostBody>, t: Throwable) {
                }
            })
            call.respond(HttpStatus.OK_200)
        //}
    }
}


class PlaceNotes {
    companion object {
        const val getPlaceMainGroupListNote =
            "<h1>대지역 리스트</h1>"

        const val getPlaceSubGroupListNote =
            "<h1>소지역 리스트</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Query Parameter 로 option 을 받습니다.__\n" +
                    "|파라미터 명|타입|필수여부|내용|\n" +
                    "|--------|---|---|---|\n" +
                    "|mainPlaceId|Long(Number)|Y|지역 대분류 고유번호|\n"
    }
}
