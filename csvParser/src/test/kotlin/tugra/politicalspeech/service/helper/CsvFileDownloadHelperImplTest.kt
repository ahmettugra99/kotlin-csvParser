import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import tugra.politicalspeech.service.checker.CsvFilesUrlMapCheckerImpl
import java.lang.reflect.Method


class CsvFilesUrlMapCheckerImplTest {

    @Test
    fun testIsValid() {
        val checker = mock(CsvFilesUrlMapCheckerImpl::class.java)

        val mockUrlMap = mapOf(
            "File1" to "http://example.com/file1.csv",
            "File2" to "http://example.com/file2.csv"
        )

        val isCSVFileMethod: Method = CsvFilesUrlMapCheckerImpl::class.java.getDeclaredMethod("isCSVFile", String::class.java)
        val isUrlAccessibleMethod: Method = CsvFilesUrlMapCheckerImpl::class.java.getDeclaredMethod("isUrlAccessible", String::class.java)

        isCSVFileMethod.isAccessible = true
        isUrlAccessibleMethod.isAccessible = true

        isCSVFileMethod.invoke(checker, "http://example.com/file1.csv")
        isCSVFileMethod.invoke(checker, "http://example.com/file2.csv")
        isUrlAccessibleMethod.invoke(checker, "http://example.com/file1.csv")
        isUrlAccessibleMethod.invoke(checker, "http://example.com/file2.csv")

        val isValid = checker.isValid(mockUrlMap)

        assertFalse(isValid)
    }
}
