import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.PetStoreService;
import utils.enums.PetStatus;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_OK;

public class FindPetByStatusTests {
    @Test
    public void findPetsBySingleStatusSuccessfully() {

        Response petsFoundByStatusResponse = PetStoreService.findPetsByStatus(PetStatus.AVAILABLE.getValue());

        Assertions.assertEquals(SC_OK, petsFoundByStatusResponse.getStatusCode(),
                "Get pets by single status failed. Status code: " +
                        petsFoundByStatusResponse.getStatusCode());

        JSONArray responseAsJson = responseToJSONArray(petsFoundByStatusResponse);

        Assertions.assertTrue(responseAsJson.length() > 0, "Response doesn't contain any pet");

        for (int i = 0; i < responseAsJson.length(); i++) {
            String petStatus = responseAsJson.getJSONObject(i).getString("status");

            Assertions.assertEquals(PetStatus.AVAILABLE.getValue(), petStatus,
                    "Returned pet has incorrect status. " +
                            "Expected status is '" + PetStatus.AVAILABLE.getValue() + "'. " +
                            "Actual status is '" + petStatus + "'");
        }

    }

    @Test
    public void findPetsByMultipleStatusesSuccessfully() {

        Response petsFoundByStatusResponse = PetStoreService
                .findPetsByStatus(PetStatus.SOLD.getValue(), PetStatus.PENDING.getValue());

        Assertions.assertEquals(SC_OK, petsFoundByStatusResponse.getStatusCode(),
                "Get pets by multiple statuses failed. " +
                        "Status code: " + petsFoundByStatusResponse.getStatusCode());

        JSONArray responseAsJson = responseToJSONArray(petsFoundByStatusResponse);

        Assertions.assertTrue(responseAsJson.length() > 0, "Response doesn't contain any pet");

        for (int i = 0; i < responseAsJson.length(); i++) {
            String petStatus = responseAsJson.getJSONObject(i).getString("status");

            Assertions.assertTrue(Arrays.asList(PetStatus.SOLD.getValue(), PetStatus.PENDING.getValue())
                            .contains(petStatus),
                    "Returned pet status is incorrect. Expected status is one of the following '" +
                            PetStatus.SOLD.getValue() + "' or '" +
                            PetStatus.PENDING.getValue() + "'. " +
                            "Actual status is '" + petStatus + "'");
        }

    }

    // According to swagger documentation I expected to get 400 Invalid status value
    // but even with invalid status in request I get 200 status code and an empty array in response body
    @Test
    public void findPetsWithInvalidStatusParameter() {

        PetStoreService
                .findPetsByStatus("dummy")
                .then()
                .statusCode(SC_OK)
                .assertThat()
                .body("", Matchers.hasSize(0));
    }

    private JSONArray responseToJSONArray(Response response) {
        JSONArray responseAsJson;

        try {
            responseAsJson = new JSONArray(response.getBody().asString());

        } catch (JSONException e) {
            responseAsJson = new JSONArray("");
        }

        return responseAsJson;
    }
}
