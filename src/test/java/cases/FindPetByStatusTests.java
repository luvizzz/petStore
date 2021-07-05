package cases;

import domain.Pet;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

public class FindPetByStatusTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    @Description("Test finding existing Pet by status expect success")
    void findPetByStatus_expectSuccess() {
        //WHEN
        Response response = petSteps.findPetByStatus(pet.getStatus());

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId()));
    }

    @Test
    @Description("Test finding existing Pet without providing status expect empty list")
    void findPetByStatusWithoutStatusProvided_expectEmptyList() {
        //WHEN
        Response response = petSteps.findPetByStatus();

        //THEN
        petSteps.assertResponseContainsList(response, List.of());
    }

    @Test
    @Description("Test finding existing Pet by providing wrong status expect not found")
    void findPetByStatusWithWrongStatusProvided_expectPetNotFound() {
        //WHEN
        Response response = petSteps.findPetByStatus(pet.getStatus() + "someBadStatus");

        //THEN
        petSteps.assertResponseContainsList(response, List.of());
    }

    @Test
    @Description("Test finding multiple Pets by providing multiple statuses expect success")
    void findMultiplePetsByMultipleStatuses_expectSuccess() {
        //GIVEN
        Pet otherPet = new Pet.Builder()
                .withId((long) Utils.randomId())
                .withName("someTestName")
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .withStatus(Utils.randomStatus())
                .build();
        petSteps.addPet(otherPet);

        //WHEN
        Response response = petSteps.findPetByStatus(pet.getStatus(), otherPet.getStatus());

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId(), otherPet.getId()));
    }

    @Test
    @Description("Test finding single Pet by providing multiple statuses expect success")
    void findSinglePetByMultipleStatuses_expectSuccess() {
        //WHEN
        Response response = petSteps.findPetByStatus(pet.getStatus(), "someOtherStatus");

        //THEN
        petSteps.assertResponseContainsList(response, List.of(pet.getId()));
    }
}
