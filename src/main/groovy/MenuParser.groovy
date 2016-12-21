import org.apache.tika.Tika

class MenuParser {

    def tika = new Tika()
    def pdf

    MenuParser(pdf) {
        this.pdf = pdf
    }

    def getLinesFromPdf() {
        def inputStream = new ByteArrayInputStream(pdf)
        def rawLines = tika.parseToString inputStream
        rawLines.readLines().collect{ it.trim() }.findAll { !it.isEmpty() }
    }
}