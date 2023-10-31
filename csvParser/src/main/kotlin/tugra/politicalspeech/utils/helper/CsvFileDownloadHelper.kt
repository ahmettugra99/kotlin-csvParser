package tugra.politicalspeech.service.helper

import org.springframework.stereotype.Component
import tugra.politicalspeech.service.model.SpeechData

@Component
interface CsvFileDownloadHelper {
    fun getCsvFilesFromUrls(urls: List<String>): List<SpeechData>
}