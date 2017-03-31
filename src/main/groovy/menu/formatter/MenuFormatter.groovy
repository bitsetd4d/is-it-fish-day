package menu.formatter

abstract class MenuFormatter {

    def lines
    def formattedLines

    static endsInPounds = ~/.*?(£\d+\.\d+)$/
    static endsInPence = ~/.*?(\d+p)$/

    MenuFormatter(lines) {
        this.lines = lines
        formatLines()
    }

    abstract shouldStartNewLine(line);
    abstract isHeading(line)
    abstract isWarning(line)

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

    def format(line) {
        if (isWarning(line)) {
            return "`$line`"
        }
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
}
