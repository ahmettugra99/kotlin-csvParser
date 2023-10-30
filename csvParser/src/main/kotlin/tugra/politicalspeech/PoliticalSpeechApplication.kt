package tugra.politicalspeech

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class PoliticalSpeechApplication

fun main(args: Array<String>) {
    runApplication<PoliticalSpeechApplication>(*args)
}
