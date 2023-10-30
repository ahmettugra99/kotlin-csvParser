package tugra.politicalspeech.service

import org.springframework.stereotype.Service
import tugra.politicalspeech.dto.SpeechProcessorResponseDto
import tugra.politicalspeech.exception.InvalidInputException
import tugra.politicalspeech.service.checker.CsvFilesUrlMapChecker;
import tugra.politicalspeech.service.helper.CsvFileDownloadHelper
import tugra.politicalspeech.service.model.SpeechData

/**
 * Implementation of SpeecheProcessorService for processing political speech data.
 */
@Service
class SpeechProcessorServiceImpl(
        private val csvFilesUrlMapChecker: CsvFilesUrlMapChecker,
        private val csvFileDownloadHelper: CsvFileDownloadHelper
) : SpeechProcessorService {

    /**
     * Processes political speech data from the given CSV file URLs and returns a response containing
     * information about the most active politician, the politician with the most speeches on a specified topic,
     * and the politician with the fewest words spoken overall.
     *
     * @param csvFilesUrlMap A map of addresses and their corresponding URLs.
     * @return A SpeechProcessorResponseDto containing the results of the speech data analysis.
     * @throws InvalidInputException if the provided URLs are not reachable or do not point to valid CSV files.
     */
    override fun processSpeech(csvFilesUrlMap: Map<String, String>): SpeechProcessorResponseDto {
        if (csvFilesUrlMapChecker.isValid(csvFilesUrlMap)) {
            val speeches: List<SpeechData> = csvFileDownloadHelper.getCsvFilesFromUrls(csvFilesUrlMap.values.toList())
            val mostSpeechesIn2013 = speeches.politicianWithMostSpeeches(2013)
            val mostSpeechesOnHomelandSecurity = speeches.politicianWithMostSpeechesOnTopic("homeland security")
            val fewestWordsOverall = speeches.politicianWithFewestWords()

            return SpeechProcessorResponseDto(mostSpeechesIn2013, mostSpeechesOnHomelandSecurity, fewestWordsOverall)

        } else {
            throw InvalidInputException("The URLs are not reachable or not a CSV file")
        }
    }

    /**
     * Finds the politician with the most speeches in a specific year.
     *
     * @param dataList The list of SpeechData objects to analyze.
     * @return The name of the politician with the most speeches in the specified year.
     */
    fun List<SpeechData>.politicianWithMostSpeeches(year: Int): String? {
        return this.filter { it.date.year == year }
            .groupingBy { it.speaker }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
    }

    /**
     * Finds the politician with the most speeches on a specified topic.
     *
     * @param dataList The list of SpeechData objects to analyze.
     * @param topic The topic to consider when counting speeches.
     * @return The name of the politician with the most speeches on the specified topic.
     */
    fun List<SpeechData>.politicianWithMostSpeechesOnTopic(topic: String): String? {
        return this.filter { it.topic.equals(topic, ignoreCase = true) }
            .groupingBy { it.speaker }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
    }

    /**
     * Finds the politician with the fewest total words spoken.
     *
     * @param dataList The list of SpeechData objects to analyze.
     * @return The name of the politician with the fewest words spoken overall.
     */
    fun List<SpeechData>.politicianWithFewestWords(): String? {
        return this.groupingBy { it.speaker }
            .fold(0L) { accumulator, element -> accumulator + element.words }
            .minByOrNull { it.value }
            ?.key
    }
}
