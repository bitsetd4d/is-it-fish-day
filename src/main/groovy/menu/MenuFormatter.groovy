package menu

class MenuFormatter {

    def lines
    def formattedLines

    static endsInPounds = ~/.*?(£\d+\.\d+)$/
    static endsInPence = ~/.*?(\d+p)$/

    MenuFormatter(lines) {
        this.lines = lines
        formatLines()
    }

    def formatLines() {
        def current = ""
        formattedLines = []
        lines.forEach {
            current += " " + format(it)
            if (shouldStartNewLine(it)) {
                formattedLines << current
                current = ""
            }
        }
    }

    def shouldStartNewLine(line) {
        if (formattedLines.size() <= 2) return true
        if (isSeparator(line)) return true
        if (isPriceAtEnd(line)) return true
        if (isHeading(line)) return true
        false
    }

    def format(line) {
        if (isHeading(line)) {
            return "*$line*"
        }
        if (isSeparator(line)) {
            return "--------------------------------------"
        }
        line = highlightRegexGroup(endsInPounds, line)
        highlightRegexGroup(endsInPence, line)
    }

    def highlightRegexGroup(regex, line) {
        def matcher = regex.matcher(line)
        if (matcher.matches()) {
            def matched = matcher.group(1)
            return line.replace(matched, "_" + matched + "_")
        }
        line
    }

    static isSeparator(line) {
        line.contains('……')
    }

    static isPriceAtEnd(line) {
        if (endsInPounds.matcher(line).matches()) return true
        if (endsInPence.matcher(line.toLowerCase()).matches()) return true
        false
    }

    static isHeading(String line) {
        line = line.toLowerCase()
        if (line.contains("soups")) return true
        if (line.contains("grab a snack")) return true
        if (line.contains("live well")) return true
        if (line.contains("event")) return true
        if (line.contains("dessert")) return true
        if (line.contains("recommended")) return true
        false
    }
}
