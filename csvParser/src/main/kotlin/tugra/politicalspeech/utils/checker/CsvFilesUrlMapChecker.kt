package tugra.politicalspeech.service.checker

import org.springframework.stereotype.Component

@Component
interface CsvFilesUrlMapChecker {
    fun isValid(csvFilesUrlMap: Map<String, String>): Boolean
}