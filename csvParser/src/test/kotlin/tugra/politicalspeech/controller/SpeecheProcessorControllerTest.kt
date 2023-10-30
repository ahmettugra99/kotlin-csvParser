import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.MockMvc
import tugra.politicalspeech.controller.SpeecheProcessorController
import tugra.politicalspeech.dto.SpeechProcessorResponseDto
import tugra.politicalspeech.service.SpeechProcessorService

class SpeecheProcessorControllerTest {
    private lateinit var controller: SpeecheProcessorController
    private lateinit var speechProcessorService: SpeechProcessorService
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        speechProcessorService = mock(SpeechProcessorService::class.java)
        controller = SpeecheProcessorController(speechProcessorService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testProcessSpeech() {
        val csvFilesUrlMap = mapOf("file1.csv" to "url1", "file2.csv" to "url2")
        val expectedResponse = SpeechProcessorResponseDto("","","")

        `when`(speechProcessorService.processSpeech(csvFilesUrlMap)).thenReturn(expectedResponse)

        mockMvc.perform(MockMvcRequestBuilders.get("/speech/evaluation")
            .param("csvFilesUrlMap[file1.csv]", "url1")
            .param("csvFilesUrlMap[file2.csv]", "url2")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
