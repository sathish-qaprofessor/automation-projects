package org.catsapi.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.catsapi.models.CatsBreed;
import org.framework.base.ApiTestBase;
import org.catsapi.endpoints.CatsApiEndpoints;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CatsApiTests extends ApiTestBase {

    @Test
    public void getCatsBreedsInfoTest() {
        createTestCase("Verify different types of cats breed using '" + CatsApiEndpoints.CAT_BREEDS + "' endpoint.", () -> {
            AtomicReference<JsonPath> jsonPath = new AtomicReference<>();
            testStep("Send '" + CatsApiEndpoints.CAT_BREEDS + "' endpoint request and check status code '200'.", () -> {
                Response response = getCatsBreedsResponse();
                jsonPath.set(response.jsonPath());
                response.then().body(matchesJsonSchemaInClasspath("catsapi-schemas/cats_breeds_schema.json"));
            });

            testStep("Verify the list of all cats breeds in the list.", () -> {
                List<CatsBreed> catsBreedList = jsonPath.get().getList("$", CatsBreed.class);
                loggerService.info("Total Cats Breeds: " + catsBreedList.size());
                catsBreedList.forEach(catsBreed -> {
                    loggerService.info("Breed Name: " + catsBreed.name
                            + ", Origin: " + catsBreed.id
                            + ", Weight: " + catsBreed.weight.metric + " kg"
                    );
                });
            });
        });
    }

    @Test
    public void getCatImagesByBreedIdTest() {
        createTestCase("Search and get cat image by its breed id using '" + CatsApiEndpoints.CAT_IMAGES_SEARCH+ "' endpoint.", () -> {
            AtomicReference<JsonPath> jsonPath = new AtomicReference<>();

            testStep("Send '" + CatsApiEndpoints.CAT_BREEDS + "' endpoint request and check status code '200'.", () -> {
                jsonPath.set(getCatsBreedsResponse().jsonPath());
            });

            testStep("Get first cat breed id from the list and search its image using '" + CatsApiEndpoints.CAT_IMAGES_SEARCH + "' endpoint.", () -> {
                List<CatsBreed> catsBreedList = jsonPath.get().getList("$", CatsBreed.class);
                String breedId = catsBreedList.get(0).id;
                loggerService.info("Searching image for Breed Name: " + catsBreedList.get(0).name + ", Breed ID: " + breedId);

                Response response = service()
                        .setBaseUri(CatsApiEndpoints.CATS_BASE_URI)
                        .addQueryParam("breed_ids", breedId)
                        .addQueryParam("limit", "1")
                        .sendGetRequest(CatsApiEndpoints.CAT_IMAGES_SEARCH)
                        .validateStatusCode(200)
                        .getResponse();

                response.then().body(matchesJsonSchemaInClasspath("catsapi-schemas/cat_images_search_schemas.json"));

                JsonPath imageJsonPath = response.jsonPath();
                String imageUrl = imageJsonPath.getString("[0].url");
                loggerService.info("Image URL for Breed ID '" + breedId + "': " + imageUrl);
            });
        });
    }

    private Response getCatsBreedsResponse() {
        return service()
                .setBaseUri(CatsApiEndpoints.CATS_BASE_URI)
                .sendGetRequest(CatsApiEndpoints.CAT_BREEDS)
                .validateStatusCode(200)
                .getResponse();
    }
}
