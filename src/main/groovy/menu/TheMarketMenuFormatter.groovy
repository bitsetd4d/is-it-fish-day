package menu

import java.text.DateFormat
import java.text.SimpleDateFormat

class TheMarketMenuFormatter extends MenuFormatter {

    static final String DATE_REG_EX = "[A-Z][a-z]+ \\d{2}[a-z]{2} [A-Z][a-z]+ \\d{4}";

    TheMarketMenuFormatter(lines) {
        super(lines)
    }
    def shouldStartNewLine(line) {
        true
    }

    def isHeading(String line) {
        line = line.toLowerCase()
        if (line.contains("mains")) return true
        if (line.contains("sides")) return true
        if (line.contains("the market salad bowls")) return true
        if (line.contains("protein")) return true
        if (line.contains("original jacket or sweet potato")) return true
        false
    }

    def isWarning(line) {
        if (line.matches(DATE_REG_EX)) {
            DateFormat fmt = new SimpleDateFormat('EEEE dd MMM yyyy', Locale.UK);
            Date menuDate = fmt.parse(line.replaceAll("(?<=\\d)(st|nd|rd|th)", ""));
            return !isToday(menuDate);
        }
        false
    }

    static isToday(date){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date).equals(fmt.format(new Date()));
    }
}

