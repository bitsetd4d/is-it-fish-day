import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

class WeatherReporterTest {

    def json

    @Before
    void setUp() {
        def currently = [summary: 'Windy', nearestStormDistance: 35, precipProbability: 0, temperature: 10.68]
        def hourly0 = [time: 1482328800, summary: 'Partly Cloudy', precipProbability: 0.22, precipType: 'rain', temperature: 10.1]
        def hourly1 = [time: 1482332400, summary: 'Hellish', precipProbability: 0, temperature: 100.0]
        json = [currently: currently, hourly: [data: [hourly0, hourly1]]]
    }

    @Test
    void checkSampleJsonResponse() {
        def sampleJson = getClass().getResourceAsStream("sample-dark-sky-response.json").text
        def jsonSlurper = new JsonSlurper()
        def json = jsonSlurper.parseText sampleJson
        def weatherReporter = new WeatherReporter(json)
        weatherReporter.with {
            assert 'Partly Cloudy 10.5C. Nearest storm 35 miles.' == currentWeatherReport
            assert '14:12 : Partly Cloudy 11C' == getWeatherReportForNextHour(0)
            assert '15:12 : Partly Cloudy 10C' == getWeatherReportForNextHour(1)
        }
    }

    @Test
    void verifySampleResponseProcessedCorrectly() {
        def weatherReporter = new WeatherReporter(json)
        weatherReporter.with {
            assert 'Windy 10.5C. Nearest storm 35 miles.' == currentWeatherReport
            assert '14:12 : Partly Cloudy 10C. Chance of rain is 22%' == getWeatherReportForNextHour(0)
            assert '15:12 : Hellish 100C' == getWeatherReportForNextHour(1)
        }
    }

    @Test
    void verifyStormNearbyReported() {
        json.currently.nearestStormDistance = 99
        def weatherReporter = new WeatherReporter(json)
        weatherReporter.with {
            assert 'Windy 10.5C. Nearest storm 99 miles.' == currentWeatherReport
        }
    }

    @Test
    void verifyNoStormNearbyIsNotReported() {
        json.currently.nearestStormDistance = 0
        def weatherReporter = new WeatherReporter(json)
        assert 'Windy 10.5C' == weatherReporter.currentWeatherReport
    }

    @Test
    void verifyPrecipitationReported() {
        def weatherReporter = new WeatherReporter(json)
        assert '14:12 : Partly Cloudy 10C. Chance of rain is 22%' == weatherReporter.getWeatherReportForNextHour(0)
    }

    @Test
    void verifyAbsentPrecipitationNotReported() {
        def weatherReporter = new WeatherReporter(json)
        assert '15:12 : Hellish 100C' == weatherReporter.getWeatherReportForNextHour(1)
    }

    @Test
    void verifyPrecipitationWithLowChanceOfHappeningNotReported() {
        def hour0 = json.hourly.data[0]
        hour0.precipProbability = 0.09
        def weatherReporter = new WeatherReporter(json)
        assert '14:12 : Partly Cloudy 10C' == weatherReporter.getWeatherReportForNextHour(0)
    }
}