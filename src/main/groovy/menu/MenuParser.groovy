package menu

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
        def lines = rawLines.readLines().collect{ it.trim() }.findAll { !it.isEmpty() }
        if (lines.any { it.toLowerCase().contains("big breakfast")}) {
            int start = lines.findIndexOf { it.toLowerCase().contains("soup")}
            if (start > 0) {
                return lines.drop(start)
            }
        }
        lines
    }
}
