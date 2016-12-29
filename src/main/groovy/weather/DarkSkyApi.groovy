package weather

import config.SecureConfig
import groovy.json.JsonSlurper

class DarkSkyApi {

    static getApiKey() {
        SecureConfig.instance.darkSpyApiKey
    }

    static getLocation() {
        "51.489440,-0.327900"
    }

    def fetchCurrentWeatherForecast() {
        def url = "https://api.darksky.net/forecast/$apiKey/$location?units=uk2&exclude=minutely,daily,alerts,flags"
        def response = url.toURL().text
        println response
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText response
    }

}
