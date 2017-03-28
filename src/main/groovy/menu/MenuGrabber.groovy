package menu

import geb.Browser
import org.openqa.selenium.Dimension
import static Thread.sleep

/**
 * Grab the PDFs for the menus from the website
 */
class MenuGrabber {

    def browser = new Browser()
    def centralMenuPdf
    def theMarketPdf

    def grabMenus() {
        gotoFoodAtSkyMenuPage()
        selectOsterleyLocation()

        selectChooseRestaurantLink()
        downloadMenuForSkyCentral()

        selectChooseRestaurantLink()
        downloadMenuForTheMarket()
    }

    private void gotoFoodAtSkyMenuPage() {
        browser.driver.manage().window().size = new Dimension(640, 1024)
        browser.to SiteMenusPage
    }

    private void selectOsterleyLocation() {
        announce("Selecting Osterley")
        browser.siteLocationOsterley.click()
        waitForPageToCatchup()
    }

    private void selectChooseRestaurantLink() {
        announce("Choosing restaurant")
        browser.selectRestaurant.click()
        waitForPageToCatchup()
    }

    private void downloadMenuForSkyCentral() {
        announce("Choosing Sky Central")
        browser.skyCentral.click()
        waitForPageToCatchup()
        centralMenuPdf = downloadMenu()
    }

    private void downloadMenuForTheMarket() {
        announce("Choosing The Market")
        browser.theMarket.click()
        waitForPageToCatchup()
        theMarketPdf = downloadMenu()
    }

    private downloadMenu() {
        def menuDownloadUrl = browser.downloadMenu.@href
        def pdf = browser.downloadBytes menuDownloadUrl
        println "Downloaded menu - ${pdf.length} bytes"
        pdf
    }

    static void waitForPageToCatchup() {
        sleep 1000
    }

    static void announce(message) {
        println()
        println message
        println '-' * message.toString().size()
    }
}