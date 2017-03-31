package menu.formatter

import java.text.DateFormat
import java.text.SimpleDateFormat

class TheMarketMenuFormatter extends MenuFormatter {

    static final String DATE_REG_EX = "[A-Z][a-z]+ (\\d{2})[a-z]{0,2} [A-Z][a-z]+ \\d{4}";

    TheMarketMenuFormatter(lines) {
        super(lines)
    }
    def shouldStartNewLine(line) {
        true
    }

    def isHeading(line) {
        line = line.toLowerCase()
        if (line.contains("mains")) return true
        if (line.contains("sides")) return true
        if (line.contains("pasta bar")) return true
        if (line.contains("the market salad bowls")) return true
        if (line.contains("protein")) return true
        if (line.contains("original jacket or sweet potato")) return true
        false
    }

    def isWarning(line) {
        if (line.matches(DATE_REG_EX)) {
            DateFormat fmt = new SimpleDateFormat('EEEE dd MMMM yyyy', Locale.UK);
            return !fmt.format(new Date()).equals(line.replaceAll("(?<=\\d)(st|nd|rd|th)", ""));
        }
        false
    }
}

