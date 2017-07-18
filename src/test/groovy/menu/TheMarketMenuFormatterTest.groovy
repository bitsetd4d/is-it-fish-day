package menu

import com.google.common.base.Preconditions
import menu.formatter.TheMarketMenuFormatter
import org.junit.Test

import java.text.SimpleDateFormat
import java.util.regex.Pattern

class TheMarketMenuFormatterTest {

    @Test
    void shouldTreatKeyWordsAsHeadings(){
        def mains = "Mains - £2.60"
        def salads = "The Market Salad Bowls (V) - £1.60"
        def protein = "Protein – £1.05"
        def pastaBar = "Pasta Bar – £2.60"
        def jacketPotato = "Original Jacket or Sweet Potato – £0.40"
        // when
        def mainsHeading = new TheMarketMenuFormatter([mains]).isHeading(mains)
        def saladsHeading = new TheMarketMenuFormatter([salads]).isHeading(salads)
        def proteinHeading = new TheMarketMenuFormatter([protein]).isHeading(protein)
        def pastaBarHeading = new TheMarketMenuFormatter([pastaBar]).isHeading(pastaBar)
        def jacketPotatoHeading = new TheMarketMenuFormatter([jacketPotato]).isHeading(jacketPotato)
        // then
        assert mainsHeading
        assert saladsHeading
        assert proteinHeading
        assert pastaBarHeading
        assert jacketPotatoHeading
    }

    @Test
    void shouldNotTreatKeyWordsAsHeadings(){
        def peas = "Garden peas – 40p"
        def salad = "Miso Green Bean, Edamame & Cucumber Salad"
        def date = "Wednesday 29th March 2017"
        def sauce = "With mash potato & cream & mushroom sauce"
        // when
        def peasHeading = new TheMarketMenuFormatter([peas]).isHeading(peas)
        def saladHeading = new TheMarketMenuFormatter([salad]).isHeading(salad)
        def dateHeading = new TheMarketMenuFormatter([date]).isHeading(date)
        def sauceHeading = new TheMarketMenuFormatter([sauce]).isHeading(sauce)
        // then
        assert !peasHeading
        assert !saladHeading
        assert !dateHeading
        assert !sauceHeading
    }

    @Test
    void shouldBeWarningIfTheLineIsYesterday() {
        // given
        Calendar yesterday = getCalendarSwappedByDays(-1)
        def yesterdayLine = includeDayOfMonthSuffix(yesterday)
        List<String> lines = [] << yesterdayLine
        TheMarketMenuFormatter formatter = new TheMarketMenuFormatter(lines)
        // when
        def aWarning = formatter.isWarning(yesterdayLine)
        // then
        assert aWarning
    }

    @Test
    void shouldNotBeWarningIfTheLineIsToday() {
        // given
        def calendar = Calendar.getInstance(Locale.UK)
        def tomorrowLine = includeDayOfMonthSuffix(calendar)
        List<String> lines = [] << tomorrowLine
        TheMarketMenuFormatter formatter = new TheMarketMenuFormatter(lines)
        // when
        def aWarning = formatter.isWarning(tomorrowLine)
        // then
        assert !aWarning
    }

    @Test
    void shouldBeWarningIfTheLineIsTomorrow() {
        // given
        Calendar tomorrow = getCalendarSwappedByDays(1)
        def tomorrowLine = includeDayOfMonthSuffix(tomorrow)
        List<String> lines = [] << tomorrowLine
        TheMarketMenuFormatter formatter = new TheMarketMenuFormatter(lines)
        // when
        def aWarning = formatter.isWarning(tomorrowLine)
        // then
        assert aWarning
    }

    @Test
    void shouldNotBeWarningIfTheLineNotADate() {
        // given
        def notADateLine = "I'm not a date"
        List<String> lines = [] << notADateLine
        TheMarketMenuFormatter formatter = new TheMarketMenuFormatter(lines)
        // when
        def aWarning = formatter.isWarning(notADateLine)
        // then
        assert !aWarning
    }

    def getCalendarSwappedByDays(daysFromNow) {
        def calendar = Calendar.getInstance(Locale.UK)
        calendar.add(Calendar.DAY_OF_YEAR, daysFromNow)
        calendar
    }
    def includeDayOfMonthSuffix(calendar) {
        def dayOfMonthSuffix = getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH))
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy",  Locale.ENGLISH)
        def rawDate = dateFormat.format(calendar.time)
        Pattern p = Pattern.compile(TheMarketMenuFormatter.DATE_REG_EX)
        def matcher = p.matcher(rawDate)
        if (matcher.find()) {
            def start = matcher.start(1)
            def end = matcher.end(1)
            def dayOfTheMonth = matcher.group(1)
            return rawDate.substring(0, start) + dayOfTheMonth + dayOfMonthSuffix + rawDate.substring(end)
        }
    }

    String getDayOfMonthSuffix(final int n) {
        Preconditions.checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n)
        if (n >= 11 && n <= 13) {
            return "th"
        }
        switch (n % 10) {
            case 1: return "st"
            case 2: return "nd"
            case 3: return "rd"
            default: return "th"
        }
    }
}
