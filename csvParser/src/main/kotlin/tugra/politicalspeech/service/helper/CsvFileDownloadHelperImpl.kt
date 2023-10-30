package tugra.politicalspeech.service.helper

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.stereotype.Component
import tugra.politicalspeech.exception.InvalidInputException
import tugra.politicalspeech.service.model.SpeechData
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDate

/**
 * Implementation of CsvFileDownloadHelper for downloading and parsing CSV files from URLs.
 */
@Component
class CsvFileDownloadHelperImpl : CsvFileDownloadHelper {

    /**
     * Downloads and parses CSV files from the provided list of URLs and returns a list of SpeechData objects.
     *
     * @param urls A list of URLs from which to download and parse CSV files.
     * @return A list of SpeechData objects parsed from the CSV files.
     * @throws InvalidInputException if the CSV file format is invalid or the file is corrupted.
     */
    override fun getCsvFilesFromUrls(urls: List<String>): List<SpeechData> {
        val csvData: MutableList<SpeechData> = mutableListOf()

        for (url in urls) {
            csvData.addAll(parseCsvFromUrl(url))
        }
        return csvData
    }

    /**
     * Parses a CSV file from the given URL and returns a list of SpeechData objects.
     *
     * @param url The URL of the CSV file to be parsed.
     * @return A list of SpeechData objects parsed from the CSV file.
     * @throws InvalidInputException if the CSV file format is invalid or the file is corrupted.
     */
    fun parseCsvFromUrl(url: String): List<SpeechData> {
        val speechData: MutableList<SpeechData> = mutableListOf()

        try {
            val csvParser = CSVParserBuilder().withSeparator(';').build()
            val csvReader = CSVReaderBuilder(InputStreamReader(URL(url).openStream()))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()

            var record: Array<String>?
            while (csvReader.readNext().also { record = it } != null) {
                if (record!!.size == 4) {
                    val speaker = record!![0].trim()
                    val topic = record!![1].trim()
                    val date = LocalDate.parse(record!![2].trim())
                    val words = record!![3].trim().toLong()
                    val speech = SpeechData(speaker, topic, date, words)
                    speechData.add(speech)
                } else {
                    throw InvalidInputException("Invalid CSV format. Each record should have 4 fields.")
                }
            }
            csvReader.close()
        } catch (e: Exception) {
            throw InvalidInputException("CSV file cannot be parsed. File format is not valid or file is corrupted.")
        }

        return speechData
    }


}