package tugra.politicalspeech.service

import tugra.politicalspeech.dto.SpeechProcessorResponseDto

interface SpeechProcessorService {
    fun processSpeech(csvFilesUrlMap: Map<String, String>): SpeechProcessorResponseDto

}