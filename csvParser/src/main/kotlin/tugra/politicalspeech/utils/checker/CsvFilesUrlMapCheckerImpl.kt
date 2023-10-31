package tugra.politicalspeech.service.checker

import org.springframework.stereotype.Component
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Implementation of CsvFilesUrlMapChecker that checks the validity of a given URL map.
 */
@Component
class CsvFilesUrlMapCheckerImpl : CsvFilesUrlMapChecker {

    /**
     * Checks the validity of a given URL map.
     *
     * @param csvFilesUrlMap A map of addresses and their corresponding URLs.
     * @return true if all URLs are valid, false otherwise.
     */
    override fun isValid(csvFilesUrlMap: Map<String, String>): Boolean {
        return csvFilesUrlMap.all { (address, url) ->
            address.isNotBlank() &&
                    url.isNotBlank() &&
                    isUrlAccessible(url) &&
                    isCSVFile(url)
        }
    }

    /**
     * Checks if the given URL points to a CSV file.
     *
     * @param url The URL to check.
     * @return true if the URL points to a CSV file, false otherwise.
     */
    private fun isCSVFile(url: String): Boolean {
        return url.endsWith(".csv", ignoreCase = true)
    }


    /**
     * Checks if the given URL is accessible by attempting a HEAD request.
     *
     * @param url The URL to check for accessibility.
     * @return true if the URL is accessible, false otherwise.
     */
   private fun isUrlAccessible(url: String): Boolean {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connect()
            connection.responseCode == HttpURLConnection.HTTP_OK
        } catch (e: IOException) {
            false
        }
    }
}
