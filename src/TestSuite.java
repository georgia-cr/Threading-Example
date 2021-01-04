// imports JUnit 4 modules
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/*
runs the other tests
 */
@RunWith(Categories.class)
@Categories.IncludeCategory({UnitTests.class, SystemTests.class})
@Suite.SuiteClasses({TestCard.class, TestCardDeck.class, TestGameFlag.class, TestUserInput.class,
        TestCardGame.class, TestPlayer.class, TestSystem.class})
public class TestSuite {

}
