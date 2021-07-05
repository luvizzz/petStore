package cases;

import domain.Pet;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class UpdatePetByPostTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    @Description("Test updating multiple data of existing Pet expect success")
    void updatePetNameAndStatus_expectSuccess() {
        //GIVEN
        String updatedName = Utils.randomName();
        String updatedStatus = Utils.randomStatus();
        Pet updatedPet = new Pet.Builder()
                .withId(pet.getId())
                .withCategory(pet.getCategory())
                .withName(updatedName)
                .withPhotoUrls(pet.getPhotoUrls())
                .withTags(pet.getTags())
                .withStatus(updatedStatus)
                .build();

        //WHEN
        Response response = petSteps.updatePetByPost(pet.getId(), updatedName, updatedStatus);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, updatedPet);
    }

    @Test
    @Tag(SMOKE_TAG)
    @Description("Test updating name of existing Pet expect success")
    void updatePetName_expectSuccess() {
        //GIVEN
        String updatedName = Utils.randomName();
        Pet updatedPet = new Pet.Builder()
                .withId(pet.getId())
                .withCategory(pet.getCategory())
                .withName(updatedName)
                .withPhotoUrls(pet.getPhotoUrls())
                .withTags(pet.getTags())
                .withStatus(pet.getStatus())
                .build();

        //WHEN
        Response response = petSteps.updatePetByPost(pet.getId(), updatedName, null);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, updatedPet);
    }

    @Test
    @Tag(SMOKE_TAG)
    @Description("Test updating status of existing Pet expect success")
    void updatePetStatus_expectSuccess() {
        //GIVEN
        String updatedStatus = Utils.randomStatus();
        Pet updatedPet = new Pet.Builder()
                .withId(pet.getId())
                .withCategory(pet.getCategory())
                .withName(pet.getName())
                .withPhotoUrls(pet.getPhotoUrls())
                .withTags(pet.getTags())
                .withStatus(updatedStatus)
                .build();

        //WHEN
        Response response = petSteps.updatePetByPost(pet.getId(), null, updatedStatus);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, updatedPet);
    }

    @Test
    @Description("Test updating nothing of existing Pet expect success")
    void updateNothing_expectSuccess() {
        //WHEN
        Response response = petSteps.updatePetByPost(pet.getId(), null, null);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    @Description("Test updating Pet providing wrong id expect not found")
    void updatePetWithWrongId_expectNotFound() {
        //GIVEN
        petSteps.deletePet(null, pet.getId());
        String updatedName = Utils.randomName();
        String updatedStatus = Utils.randomStatus();

        //WHEN
        Response response = petSteps.updatePetByPost(pet.getId(), updatedName, updatedStatus);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }
}
