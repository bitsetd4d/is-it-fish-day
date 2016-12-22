class WeatherReporter {

    def response

    WeatherReporter(response) {
        this.response = response
    }

    def getCurrentWeatherReport() {
        def temperature = roundToHalf response.currently.temperature
        def nearestStorm = response.currently.nearestStormDistance
        if (nearestStorm) {
            "${response.currently.summary} ${temperature}C. Nearest storm $nearestStorm miles."
        } else {
            "${response.currently.summary} ${temperature}C"
        }
    }

    def getWeatherReportForNextHour(index) {
        def hour = response.hourly.data[index]
        def date = new Date(hour.time * 1000L)
        def time = date.format "HH:MM"
        def temperature = roundToHalf hour.temperature
        def precipitationType = hour.precipType ?: 'precipitation'
        def precipitationChance = Math.round(hour.precipProbability * 100)
        if (precipitationChance > 10) {
            "$time : ${hour.summary} ${temperature}C. Chance of $precipitationType is ${precipitationChance}%"
        } else {
            "$time : ${hour.summary} ${temperature}C"
        }
    }

    static roundToHalf(d) {
        Math.round(d * 2) / 2
    }

}