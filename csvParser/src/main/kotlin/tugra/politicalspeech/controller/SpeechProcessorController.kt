package tugra.politicalspeech.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tugra.politicalspeech.dto.SpeechProcessorResponseDto
import tugra.politicalspeech.service.SpeechProcessorService


@RestController
@RequestMapping("/speech")
class SpeecheProcessorController(private val speechProcessorService: SpeechProcessorService) {

    @ApiOperation("Process Speech",
        notes = "Process speech using the provided CSV file URLs.",
        response = SpeechProcessorResponseDto::class)
    @GetMapping("/evaluation")
    fun processSpeech(@RequestParam csvFilesUrlMap: Map<String, String>): SpeechProcessorResponseDto {
        return speechProcessorService.processSpeech(csvFilesUrlMap)
    }
}
