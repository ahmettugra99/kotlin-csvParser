package tugra.politicalspeech.service.model

import java.time.LocalDate

data class SpeechData(
    val speaker: String,
    val topic: String,
    val date: LocalDate,
    val words: Long
)