package menu

import org.junit.Test

class SiteMenusPageLiveTest {

    @Test
    void loadPage() {
        def grabber = new MenuGrabber()
        grabber.grabMenus()

        def central = new File("build/central.pdf")
        central << grabber.centralMenuPdf

        def wxh = new File("build/wxh.pdf")
        wxh << grabber.wxhMenuPdf
    }

}