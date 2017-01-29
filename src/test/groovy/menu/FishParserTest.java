package menu;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.util.List;

public class FishParserTest {

    @Test
    public void recogniseCodAsAFishInSingleLineMenu() {
        List<String> menu = Lists.newArrayList("A disgusting mix of Cod and Prawn");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 1;
        assert menuItemsContainingFish.get(0) == "A disgusting mix of Cod and Prawn";
    }

    @Test
    public void recogniseFishInMultiLineMenu() {
        List<String> menu = Lists.newArrayList("Ewww Pangasius", "Mmmm Beef");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 1;
        assert menuItemsContainingFish.get(0) == "Ewww Pangasius";
    }

    @Test
    public void recogniseFishInCapitalLetters() {
        List<String> menu = Lists.newArrayList("Ewww BASS");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 1;
        assert menuItemsContainingFish.get(0) == "Ewww BASS";
    }


    @Test
    public void recogniseIfMenuContainsFishAnywhere() {
        List<String> menu = Lists.newArrayList("FiShCaKe");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 1;
        assert menuItemsContainingFish.get(0) == "FiShCaKe";
    }

    @Test
    public void returnsEmptyArrayIfThereAreNoFish() {
        List<String> menu = Lists.newArrayList("Pork", "Chocolate Cake", "Pie");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 0;
    }

    @Test
    public void ignoresCommonGenericItemsThatCouldContainFish() {
        List<String> menu = Lists.newArrayList("Soho Sandwich that most definitely could contain fish", "Fishy Boxed Salads");

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 0;
    }

    @Test
    public void returnsEmptyArrayIfThereIsNothingOnTheMenu() {
        List<String> menu = Lists.newArrayList();

        List<String> menuItemsContainingFish = FishParser.getMenuItemsContainingFish(menu);

        assert menuItemsContainingFish.size() == 0;
    }
}