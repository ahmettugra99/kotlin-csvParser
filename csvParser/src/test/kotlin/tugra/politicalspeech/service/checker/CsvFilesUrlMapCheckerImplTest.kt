package tugra.politicalspeech.service.checker

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import java.lang.reflect.Method

class CsvFilesUrlMapCheckerImplTest {

    private lateinit var csvFilesUrlMapChecker: CsvFilesUrlMapCheckerImpl

    @BeforeEach
    fun setUp() {
        csvFilesUrlMapChecker = CsvFilesUrlMapCheckerImpl()
    }

    @Test
    fun testIsCSVFileValidCsvUrl() {
        val validCsvUrl = "http://example.com/file.csv"
        val method: Method = csvFilesUrlMapChecker::class.java.getDeclaredMethod("isCSVFile", String::class.java)
        method.isAccessible = true
        val result = method.invoke(csvFilesUrlMapChecker, validCsvUrl) as Boolean
        assertTrue(result)
    }

    @Test
    fun testIsCSVFileInvalidCsvUrl() {
        val invalidCsvUrl = "http://example.com/file.txt"
        val method: Method = csvFilesUrlMapChecker::class.java.getDeclaredMethod("isCSVFile", String::class.java)
        method.isAccessible = true
        val result = method.invoke(csvFilesUrlMapChecker, invalidCsvUrl) as Boolean
        assertFalse(result)
    }

    @Test
    fun testIsUrlAccessibleInvalidUrl() {
        val invalidUrl = "not_a_valid_url"

        val method: Method = csvFilesUrlMapChecker::class.java.getDeclaredMethod("isUrlAccessible", String::class.java)
        method.isAccessible = true
        val result = method.invoke(csvFilesUrlMapChecker, invalidUrl) as Boolean
        assertFalse(result)
    }

    @Test
    fun testIsUrlAccessibleConnectionError() {
        val urlWithConnectionError = "http://example.com/connection_error.csv"

        val method: Method = csvFilesUrlMapChecker::class.java.getDeclaredMethod("isUrlAccessible", String::class.java)
        method.isAccessible = true
        val result = method.invoke(csvFilesUrlMapChecker, urlWithConnectionError) as Boolean
        assertFalse(result)
    }
}
