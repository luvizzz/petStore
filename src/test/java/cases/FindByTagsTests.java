package cases;

import domain.Pet;
import domain.Tag;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FindByTagsTests extends BaseTest {
    @Test
    @org.junit.jupiter.api.Tag(SMOKE_TAG)
    void findPetByTags_expectSuccess() {
        //WHEN
        Response response = petSteps.findPetByTags(pet.getTags());

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId()));
    }

    @Test
    void findPetByTagsWithoutTagsProvided_expectEmptyList() {
        //WHEN
        Response response = petSteps.findPetByTags(List.of());

        //THEN
        petSteps.assertResponseContainsList(response, List.of());
    }

    @Test
    void findPetByTagsWithWrongTagsProvided_expectPetNotFound() {
        //WHEN
        Tag unusedTag = Utils.randomTag();
        Response response = petSteps.findPetByTags(List.of(unusedTag));

        //THEN
        petSteps.assertResponseContainsList(response, List.of());
    }

    @Test
    void finMultiplePetsByMultipleTags_expectSuccess() {
        //GIVEN
        List<Tag> tags = new ArrayList<>(pet.getTags());
        tags.add(Utils.randomTag());
        Pet otherPet = new Pet.Builder()
                .withId((long) Utils.randomId())
                .withName("someTestName")
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .withTags(tags)
                .build();
        petSteps.addPet(otherPet);

        //WHEN
        Response response = petSteps.findPetByTags(tags);

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId(), otherPet.getId()));
    }

    @Test
    void finSinglePetByMultipleTags_expectSuccess() {
        //GIVEN
        List<Tag> someTags = new ArrayList<>(pet.getTags());
        someTags.add(Utils.randomTag());
        someTags.add(Utils.randomTag());

        //WHEN
        Response response = petSteps.findPetByTags(someTags);

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId()));
    }
}
