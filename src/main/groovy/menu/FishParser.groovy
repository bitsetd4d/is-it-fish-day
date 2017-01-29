package menu

class FishParser {

    static final def FISH = ["anchovy", "basa", "bass", "sablefish", "blowfish", "bluefish",
                             "bream", "brill", "butterfish", "catfish", "cod", "dogfish", "eel",
                             "flounder", "grouper", "haddock", "hake", "halibut", "herring", "kingfish",
                             "lamprey", "lingcod", "mackerel", "monkfish", "mullet", "pangasius", "pike", "pilchard",
                             "pollock", "sablefish", "salmon", "sardine", "shad", "shark", "skate", "snapper",
                             "sole", "sturgeon", "swordfish", "tilapia", "tilefish", "trout", "tuna", "turbot", "wahoo",
                             "whitefish", "whiting", "fish"]

    static final def IGNORED_STATEMENTS = ["soho sandwich", "boxed salads"]

    static List<String> getMenuItemsContainingFish(menu) {

        def menuItemsContainingFish = []

        menu.each { String menuItem ->
            if(menuItemContainsFish(menuItem.toLowerCase()) && !menuItemContainsIgnoredStatement(menuItem.toLowerCase())) menuItemsContainingFish << menuItem
        }

        menuItemsContainingFish
    }

    private static boolean menuItemContainsFish(menuItem) {
        def containsFish = false

        FISH.each { fish ->
            if(menuItem.contains(fish)) containsFish = true
        }

        return containsFish
    }

    private static boolean menuItemContainsIgnoredStatement(menuItem) {

        def containsIgnoredStatement = false

        IGNORED_STATEMENTS.each {ignoredStatement ->
            if(menuItem.contains(ignoredStatement)) containsIgnoredStatement = true
        }

        return containsIgnoredStatement
    }
}
