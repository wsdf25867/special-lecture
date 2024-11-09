package io.clean.special_lecture.interfaces.api.special_lecture

import io.clean.special_lecture.application.special_lecture.SpecialLectureService
import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.application.special_lecture.response.SpecialLectureServiceResponse
import io.clean.special_lecture.domain.special_lecture.DEFAULT_END_DATE_TIME
import io.clean.special_lecture.domain.special_lecture.DEFAULT_START_DATE_TIME
import io.clean.special_lecture.interfaces.special_lecture.SpecialLectureController
import io.clean.special_lecture.interfaces.special_lecture.request.SpecialLectureRequest
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime
import kotlin.test.Test

@WebMvcTest(SpecialLectureController::class)
class SpecialLectureControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var specialLectureService: SpecialLectureService

    @Test
    fun `특정 날짜에 신청 가능한 특강 조회 성공`() {
        // given
        val date = LocalDateTime.of(2024, 10, 4, 0, 0)
        val specialLectures = listOf(
            SpecialLectureServiceResponse(
                id = 1L,
                title = "특강1",
                capacity = 30,
                enrollStartDateTime = DEFAULT_START_DATE_TIME,
                enrollEndDateTime = DEFAULT_END_DATE_TIME,
                lecturers = listOf()
            ),
            SpecialLectureServiceResponse(
                id = 2L,
                title = "특강2",
                capacity = 10,
                enrollStartDateTime = DEFAULT_START_DATE_TIME,
                enrollEndDateTime = DEFAULT_END_DATE_TIME,
                lecturers = listOf()
            ),
        )

        // when
        `when`(specialLectureService.findAllAbleToEnroll(date)).thenReturn(specialLectures)

        // then
        mockMvc.get("/api/special-lectures/able-to-enroll") {
            param("date", "2024-10-04T00:00:00")
        }.andExpect {
            status { isOk() }
            jsonPath("$.size()") { value(2) }
            jsonPath("$[0].title") { value("특강1") }
            jsonPath("$[1].title") { value("특강2") }
        }
    }

    @Test
    fun `특정 날짜에 신청 가능한 특강이 없는 경우 빈 리스트 반환`() {
        // given
        val date = LocalDateTime.of(2024, 10, 4, 0, 0)

        // when
        `when`(specialLectureService.findAllAbleToEnroll(date)).thenReturn(emptyList())

        // then
        mockMvc.get("/api/special-lectures/able-to-enroll") {
            param("date", "2024-10-04T00:00:00")
        }.andExpect {
            status { isOk() }
            jsonPath("$.size()") { value(0) }
        }
    }

    @Test
    fun `user 가 신청한 특강을 반환`() {
        // given
        val specialLectures = listOf(
            SpecialLectureServiceResponse(
                id = 1L,
                title = "특강1",
                capacity = 30,
                enrollStartDateTime = DEFAULT_START_DATE_TIME,
                enrollEndDateTime = DEFAULT_END_DATE_TIME,
                lecturers = listOf()
            ),
            SpecialLectureServiceResponse(
                id = 2L,
                title = "특강2",
                capacity = 10,
                enrollStartDateTime = DEFAULT_START_DATE_TIME,
                enrollEndDateTime = DEFAULT_END_DATE_TIME,
                lecturers = listOf()
            ),
        )

        // when
        `when`(specialLectureService.findAllEnrolledByUserId(1)).thenReturn(specialLectures)

        // then
        mockMvc.get("/api/special-lectures") {
            param("userId", "1")
        }.andExpect {
            status { isOk() }
            jsonPath("$.size()") { value(2) }
            jsonPath("$[0].title") { value("특강1") }
            jsonPath("$[1].title") { value("특강2") }
        }
    }

    @Test
    fun `user가 신청한 특강이 없을시 빈리스트 반환`() {
        // given

        // when
        `when`(specialLectureService.findAllEnrolledByUserId(1)).thenReturn(emptyList())

        // then
        mockMvc.get("/api/special-lectures") {
            param("userId", "1")
        }.andExpect {
            status { isOk() }
            jsonPath("$.size()") { value(0) }
        }
    }

    @Test
    fun `특강 신청 성공`() {
        // given
        val specialLectureId = 1L
        val request = SpecialLectureRequest(
            userId = 100L,
            enrollDateTime = LocalDateTime.of(2024, 10, 4, 10, 0)
        )

        // when - doNothing()을 사용하여 서비스 계층 호출이 성공하는 것으로 모킹
        doNothing().`when`(specialLectureService).enroll(SpecialLectureServiceEnrollRequest(specialLectureId, request.userId, request.enrollDateTime))

        // then
        mockMvc.post(
            "/api/special-lectures/{specialLectureId}/enroll", specialLectureId)
        {
            contentType = MediaType.APPLICATION_JSON
            content = """{
                    "userId": 100,
                    "enrollDateTime": "2024-10-04T10:00:00"
                }"""
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `특강 신청 실패 - 잘못된 특강 ID`() {
        // given
        val specialLectureId = 999L
        val request = SpecialLectureRequest(
            userId = 100L,
            enrollDateTime = LocalDateTime.of(2024, 10, 4, 10, 0)
        )

        // when - 특강이 존재하지 않는 경우 예외를 발생시키도록 설정
        `when`(specialLectureService.enroll(SpecialLectureServiceEnrollRequest(specialLectureId, request.userId, request.enrollDateTime)))
            .thenThrow(IllegalArgumentException("Special lecture not found"))

        // then
        mockMvc.post(
            "/api/special-lectures/{specialLectureId}/enroll", specialLectureId){
            contentType = MediaType.APPLICATION_JSON
            content = """{
                    "userId": 100,
                    "enrollAt": "2024-10-04T10:00:00"
                }"""
        }.andExpect {
            status { isBadRequest() }
        }
    }
}
