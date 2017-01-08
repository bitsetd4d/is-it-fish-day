import menu.MenuFormatter
import menu.MenuGrabber
import menu.MenuParser
import slack.SlackAttachment
import slack.SlackField
import slack.SlackMessage
import slack.SlackSender
import weather.DarkSkyApi
import weather.WeatherReporter

class Main {

    def centralMenu
    def whxMenu

    def currentWeather
    def weatherCurrentHour
    def weatherNextHour

    static void main(args) {
        new Main().go()
    }

    void go() {
        println "Getting the menu and posting to slack"
        println "====================================="
        println ""
        println "Grabbing menus"
        grabMenusOfInterest()

        println ""
        println "Grabbing weather"
        lookupCurrentWeather()

        println ""
        println "Send to slack"
        def message = createSlackMessage()
        def sender = new SlackSender()
        sender.send(message)
        println ""
        println "Done."
    }

    void grabMenusOfInterest() {
        MenuGrabber menuGrabber = new MenuGrabber()
        menuGrabber.grabMenus()
        centralMenu = menuTextFromPdf(menuGrabber.centralMenuPdf)
        whxMenu = menuTextFromPdf(menuGrabber.wxhMenuPdf)
    }

    void lookupCurrentWeather() {
        DarkSkyApi darkSkyApi = new DarkSkyApi()
        def response = darkSkyApi.fetchCurrentWeatherForecast()
        WeatherReporter weatherReporter = new WeatherReporter(response)
        currentWeather = weatherReporter.currentWeatherReport
        weatherCurrentHour = weatherReporter.getWeatherReportForNextHour(0)
        weatherNextHour = weatherReporter.getWeatherReportForNextHour(1)
    }

    def createSlackMessage() {
        def diningRoom = new SlackField(title: 'The Dining Room')
        diningRoom.lines = centralMenu

        def delphine = new SlackField(title: 'Delphine')
        delphine.lines = whxMenu

        def menus = new SlackAttachment(title: 'Menus today', fields: [diningRoom, delphine])

        def weather = new SlackField(title: currentWeather)
        weather.lines << weatherCurrentHour
        weather.lines << weatherNextHour

        def weatherAttachment = new SlackAttachment(title: 'The Weather - Powered by Dark Sky', fields: [weather])

        new SlackMessage(title: 'Here are the menus for today', attachments: [menus, weatherAttachment])
    }

    static menuTextFromPdf(pdf) {
        def menuParser = new MenuParser(pdf)
        def formatter = new MenuFormatter(menuParser.linesFromPdf)
        formatter.formattedLines
    }
}
