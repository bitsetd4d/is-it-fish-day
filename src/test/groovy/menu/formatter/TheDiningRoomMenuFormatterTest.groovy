package menu.formatter

import menu.MenuParser
import org.junit.Test;

class TheDiningRoomMenuFormatterTest {

    @Test
    void checkBoringBitsOfCentralMenuIgnored() {
        def lines = getSampleMenu2018()
        def formatter = new TheDiningRoomMenuFormatter(lines)
        assert formatter.formattedLines.last().contains("PINEAPPLE UPSIDE DOWN CAKE")
    }

    def getSampleMenu2018() {
        def pdfAsBytes = getClass().getResourceAsStream("/dining-room-template-new-15-01.pdf").getBytes()
        def parser = new MenuParser(pdfAsBytes)
        parser.linesFromPdf
    }

}