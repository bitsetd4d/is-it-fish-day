import org.junit.Test

class SiteMenusPageIntegrationTest {

    @Test
    void loadPage() throws Exception {
        def grabber = new MenuGrabber()
        grabber.grabMenus()

        def central = new File("build/central.pdf")
        central << grabber.centralMenuPdf

        def wxh = new File("build/wxh.pdf")
        wxh << grabber.wxhMenuPdf
    }

}