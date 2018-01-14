import menu.FishParser
import menu.MenuGrabber
import menu.MenuParser
import menu.formatter.TheDiningRoomMenuFormatter
import slack.SlackAttachment
import slack.SlackField
import slack.SlackMessage
import slack.SlackSender
import weather.DarkSkyApi
import weather.WeatherReporter

class Main {

    def centralMenu
    def currentWeather
    def weatherCurrentHour
    def weatherNextHour
    def centralMenuItemsContainingFish

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
        println "Checking whether menus contain fish"
        searchMenusForFish()

        println ""
        println "Grabbing weather"
        lookupCurrentWeather()

        println ""
        println "Send to slack"
        def message = createSlackMessage()
        def sender = new SlackSender()
        sender.send(message)

        println ""
        println "Call fish hooks"
        callHook(isItFishDaySomewhere())

        println ""
        println "Done."
    }

    void grabMenusOfInterest() {
        MenuGrabber menuGrabber = new MenuGrabber()
        menuGrabber.grabMenus()
        centralMenu = menuTextFromTheDiningRoomPdf(menuGrabber.centralMenuPdf)
    }

    void lookupCurrentWeather() {
        DarkSkyApi darkSkyApi = new DarkSkyApi()
        def response = darkSkyApi.fetchCurrentWeatherForecast()
        WeatherReporter weatherReporter = new WeatherReporter(response)
        currentWeather = weatherReporter.currentWeatherReport
        weatherCurrentHour = weatherReporter.getWeatherReportForNextHour(0)
        weatherNextHour = weatherReporter.getWeatherReportForNextHour(1)
    }

    boolean isItFishDaySomewhere() {
        centralMenuItemsContainingFish.size() > 0
    }

    def callHook(boolean fishDay) {
        String hookName = fishDay ? "./fish.sh" : "./not-fish.sh"
        println "Calling $hookName"
        try {
            hookName.execute()
        } catch (Exception e) {
            e.printStackTrace()
        }
        println "Called $hookName"
    }

    def createSlackMessage() {
        def diningRoom = new SlackField(title: 'The Dining Room')
        diningRoom.lines = centralMenu

        def menus = new SlackAttachment(title: 'Menus today', fields: [diningRoom])

        def isItFishDayAtCentral = createFishDayMessageContents("Sky Central", centralMenuItemsContainingFish)

        def isItFishDay = new SlackAttachment(title: 'Is it fish day?', fields: [isItFishDayAtCentral])

        def weather = new SlackField(title: currentWeather)
        weather.lines << weatherCurrentHour
        weather.lines << weatherNextHour

        def weatherAttachment = new SlackAttachment(title: 'The Weather - Powered by Dark Sky', fields: [weather])

        new SlackMessage(title: 'Here are the menus for today', attachments: [menus, isItFishDay, weatherAttachment])
    }

    private static SlackField createFishDayMessageContents(String menuLocationName, List<String> menuItemsContainingFish) {

        def isItFishDaySlackField = new SlackField(title: String.format(":fish: Is it fish day at %s ?", menuLocationName))

        if (menuItemsContainingFish.size() > 0) {
            isItFishDaySlackField.lines = ["It looks like there may be fish in the following menu items:"] + menuItemsContainingFish
        } else {
            isItFishDaySlackField.lines = [String.format("No fish at %s today folks!", menuLocationName)]
        }

        isItFishDaySlackField
    }

    def searchMenusForFish() {
        centralMenuItemsContainingFish = FishParser.getMenuItemsContainingFish(centralMenu)
    }

    static menuTextFromTheDiningRoomPdf(pdf) {
        def menuParser = new MenuParser(pdf)
        def formatter = new TheDiningRoomMenuFormatter(menuParser.linesFromPdf)
        formatter.formattedLines
    }

}
