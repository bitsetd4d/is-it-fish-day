package menu

import geb.Page

/**
 * Describe structure of menu page
 */
class SiteMenusPage extends Page {

    static url = "https://foodatsky.com/sites-menus"
    static at = { title == 'Sites & Menus'}

    static content = {
        locations { $("#choose-location a") }
        siteLocationOsterley { locations.find { it.text() == 'OSTERLEY' } }

        selectRestaurant { $("#select-restaurant") }

        restaurants { $("#restaurants ul a") }
        skyCentral { restaurants.find { it.text() == 'THE DINING ROOM' } }
        westCrossHouse { restaurants.find { it.text() == 'WEST CROSS HOUSE' } }
        theMarket { restaurants.find { it.text() == 'THE MARKET' } }

        downloadMenu { $("#restaurant-menu a") }
    }

}