package cases;

import domain.Category;
import domain.Pet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import steps.PetSteps;
import utils.Utils;

import java.util.List;

public abstract class BaseTest {
    protected PetSteps petSteps = new PetSteps();
    protected final String NULL_API_KEY = null;
    protected final String SMOKE_TAG = "smoke";

    static Pet pet;

    @BeforeEach
    protected void setup() {
        pet = new Pet.Builder()
                .withId((long) Utils.randomId())
                .withCategory(new Category(100, "myCategoryName"))
                .withName("someTestName")
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .withTags(List.of(Utils.randomTag()))
                .withStatus(Utils.randomStatus())
                .build();
        petSteps.addPet(pet);

        petSteps.waitAWhile(1000L);
    }

    @AfterEach
    protected void tearDown() {
        petSteps.deletePet(NULL_API_KEY, pet.getId());
    }
}
