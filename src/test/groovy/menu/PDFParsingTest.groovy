package menu

import menu.MenuParser
import org.apache.tika.Tika
import org.junit.Test

class PDFParsingTest {

    final String expectedFirstLine = '21ST  DECEMBER OPENING TIMES 12.00pm-14.30pm'
    final String expectedLastLine = 'Please Speak To A Member Of Our Team'

    @Test
    void readSamplePdfUsingTika() {
        def tika = new Tika()
        def menuStream = getClass().getResourceAsStream("sample-menu-central.pdf")
        def textContentOfPdf = tika.parseToString(menuStream).trim()
        println textContentOfPdf
        assert textContentOfPdf.contains(expectedFirstLine)
        assert textContentOfPdf.contains('CHRISTMAS PUDDING')
        assert textContentOfPdf.endsWith(expectedLastLine)
    }

    @Test
    void checkMenuLinesPresentWithParser() {
        def pdfAsBytes = getClass().getResourceAsStream("sample-menu-central.pdf").getBytes()
        def parser = new MenuParser(pdfAsBytes)
        def menuLines = parser.linesFromPdf
        menuLines.forEach { println "Menu: $it" }
        assert menuLines.findAll{ it.isEmpty() }.isEmpty()
        assert menuLines.size() == 38
        assert menuLines.head() == expectedFirstLine
        assert menuLines.last() == expectedLastLine
    }
}