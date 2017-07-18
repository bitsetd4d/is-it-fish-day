package menu

import menu.formatter.TheMarketMenuFormatter
import org.junit.Test

class MarketMenuReaderTest {

    @Test
    void shouldReadMenuButIgnoreAllergenAndOtherStuffAtTheEnd() {
        def pdfAsBytes = getClass().getResourceAsStream("/sample-menu-market.pdf").getBytes()
        def menuParser = new MenuParser(pdfAsBytes)
        def formatter = new TheMarketMenuFormatter(menuParser.linesFromPdf)
        List<String> output = formatter.formattedLines
        assert hasLineContainingWord(output, "beans")
        assert !hasLineContainingWord(output, "allergen")
        assert !hasLineContainingWord(output, "peek")
    }

    static hasLineContainingWord(List<String> output, String word) {
        output.any { line -> line.toLowerCase().contains(word) }
    }

}
