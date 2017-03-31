package menu

import menu.formatter.MenuFormatter
import org.junit.Test

class MenuFormatterTest {

    @Test
    void recognisesSeparator() throws Exception {
        def separator = '……………………………………………………………………………..'
        def normal = 'Some noodles'
        assert MenuFormatter.isSeparator(separator)
        assert !MenuFormatter.isSeparator(normal)
    }

    @Test
    void recognisesPricesAtEndInPounds() throws Exception {
        def sample = 'A Bag of fish. £2.50'
        assert MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void recognisesPriceOnOwnInPounds() throws Exception {
        def sample = '£1.50'
        assert MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void ignorePriceInPoundsThatsNotAtTheEnd() throws Exception {
        def sample = '£2.50 and then some'
        assert !MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void recognisesPriceAtEndInPence() throws Exception {
        def sample = 'Hot soup. 20p'
        assert MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void recognisesPriceOnOwnInPence() throws Exception {
        def sample = '25P'
        assert MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void ignorePriceInPenceThatsNotAtTheEnd() throws Exception {
        def sample = '25P mate'
        assert !MenuFormatter.isPriceAtEnd(sample)
    }

    @Test
    void formatExampleMenu() {
        def lines = getSampleMenu()
        MenuFormatter formatter = new MenuFormatter(lines) {

            @Override
            def shouldStartNewLine(Object line) {
                return false
            }

            @Override
            def isHeading(Object line) {
                return false
            }

            @Override
            def isWarning(Object line) {
                return false
            }
        }
        def output = formatter.formattedLines
        output.forEach {
            println "Menu $it"
        }
    }

    def getSampleMenu() {
        def pdfAsBytes = getClass().getResourceAsStream("/sample-menu-central.pdf").getBytes()
        def parser = new MenuParser(pdfAsBytes)
        parser.linesFromPdf
    }
}
