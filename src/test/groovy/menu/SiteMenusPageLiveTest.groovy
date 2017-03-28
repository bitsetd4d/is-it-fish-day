package menu

import org.junit.Test

class SiteMenusPageLiveTest {

    @Test
    void loadPage() {
        def grabber = new MenuGrabber()
        grabber.grabMenus()

        def central = new File("build/central.pdf")
        central << grabber.centralMenuPdf

        def market = new File("build/market.pdf")
        market << grabber.theMarketPdf
    }

}