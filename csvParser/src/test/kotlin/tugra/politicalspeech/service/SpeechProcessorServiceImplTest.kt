package tugra.politicalspeech.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyMap
import org.springframework.boot.test.context.SpringBootTest
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tugra.politicalspeech.dto.SpeechProcessorResponseDto
import tugra.politicalspeech.service.checker.CsvFilesUrlMapChecker
import tugra.politicalspeech.service.helper.CsvFileDownloadHelper
import tugra.politicalspeech.service.model.SpeechData
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpeechProcessorServiceImplTest {

    @Mock
    private lateinit var csvFilesUrlMapChecker: CsvFilesUrlMapChecker

    @Mock
    private lateinit var csvFileDownloadHelper: CsvFileDownloadHelper

    @InjectMocks
    private lateinit var speechProcessorService: SpeechProcessorServiceImpl

    val speeches = listOf(
        SpeechData("Alexander Abel", "education policty", LocalDate.of(2012, 10, 30), 5310L),
        SpeechData("Bernhard Belling", "coal subsidies", LocalDate.of(2012, 11, 5), 1210L),
        SpeechData("Caesare Collins", "coal subsidies", LocalDate.of(2012, 11, 6), 1119L),
        SpeechData("Alexander Abel", "homeland security", LocalDate.of(2012, 12, 11), 911L))


    @BeforeEach
    fun setUp() {

    }

    @Test
    fun testProcessSpeech() {
        `when`(csvFilesUrlMapChecker.isValid(anyMap())).thenReturn(true)
        `when`(csvFileDownloadHelper.getCsvFilesFromUrls(Mockito.anyList())).thenReturn(speeches)

        MockitoAnnotations.initMocks(this)

        val csvFilesUrlMap = mapOf("csvTest1.csv" to "url1", "csvTest2.csv" to "url2")
        val expectedResponse = SpeechProcessorResponseDto(null,"Alexander Abel", "Caesare Collins")
        val response = speechProcessorService.processSpeech(csvFilesUrlMap)

        assertEquals(expectedResponse, response)
    }


}