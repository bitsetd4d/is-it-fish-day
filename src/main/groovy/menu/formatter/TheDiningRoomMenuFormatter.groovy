package menu.formatter

class TheDiningRoomMenuFormatter extends MenuFormatter {

    boolean reachedTheEnd

    TheDiningRoomMenuFormatter(lines) {
        super(lines)
    }

    def shouldStartNewLine(line) {
        if (formattedLines.size() <= 2) return true
        if (isSeparator(line)) return true
        if (isPriceAtEnd(line)) return true
        if (isHeading(line)) return true
        false
    }

    def isHeading(line) {
        line = line.toLowerCase()
        if (line.contains("soups")) return true
        if (line.contains("grab a snack")) return true
        if (line.contains("live well")) return true
        if (line.contains("event")) return true
        if (line.contains("dessert")) return true
        if (line.contains("recommended")) return true
        if (line.contains("vegetarian") && line.contains("main")) return true
        if (line.contains("vegeterian") && line.contains("main")) return true // SIC
        false
    }

    def isWarning(line) {
        return false
    }

    @Override
    def shouldShowLine(line) {
        reachedTheEnd = checkReachedTheBoringBits(line)
        return !reachedTheEnd
    }

    def checkReachedTheBoringBits(line) {
        if (reachedTheEnd) return true
        line = line.toLowerCase()
        if (line.contains("opening times")) return true
        if (line.contains("allergen information")) return true
        if (line.contains("sneak peak")) return true
        false
    }
}
