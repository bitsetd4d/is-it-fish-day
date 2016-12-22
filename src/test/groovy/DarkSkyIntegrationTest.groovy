import groovy.json.JsonSlurper
import org.junit.Test

class DarkSkyIntegrationTest {

    static getApiKey() {
        "97aeac6aa2baf5b6b73e6d5100347908"
    }

    static getLocation() {
        "51.489440, -0.327900"
    }

    static getSampleJsonResponse() {
        getResourceAsStream("sample-dark-sky-response.json").text
    }

    //@Test
    void callService() {
        def url = "https://api.darksky.net/forecast/$apiKey/$location?units=uk2&exclude=minutely,daily,alerts,flags"
        def response = url.toURL().text
        println response
        def jsonSlurper = new JsonSlurper()
        def json = jsonSlurper.parseText response
        println json
    }

    @Test
    void parseJson() {
        def jsonSlurper = new JsonSlurper()
        def overall = jsonSlurper.parseText sampleJsonResponse
        println "Currently ${overall.currently.summary}"
        println "Current Temperature ${overall.currently.temperature} C"
        println "Nearest storm ${overall.currently.nearestStormDistance} miles"
        println ""
        def hours = overall.hourly.data
        printHour(hours[0])
        printHour(hours[1])
        printHour(hours[2])
        printHour(hours[3])
        printHour(hours[4])
        printHour(hours[5])
        printHour(hours[6])
        printHour(hours[7])
        printHour(hours[8])
        printHour(hours[9])
        printHour(hours[10])
    }

    def printHour(hour) {
        def date = new Date(hour.time * 1000L)
        def time = date.format("HH:MM")
        def temperature = roundToHalf(hour.temperature)
        def precipType = hour.precipType ?: 'precipitation'
        def precipChance = Math.round(hour.precipProbability * 100)
        println "Forecast $time : ${hour.summary}. ${temperature}C, chance of $precipType is ${precipChance}%"
    }

    static roundToHalf(d) {
        Math.round(d * 2) / 2
    }
}
