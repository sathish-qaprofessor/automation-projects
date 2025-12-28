package org.jokesapi.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.framework.asserts.CustomSoftAssert;
import org.framework.base.ApiTestBase;
import org.jokesapi.endpoints.JokesApiEndpoints;
import org.jokesapi.models.JokeCategoryAlias;
import org.jokesapi.models.JokeDevInfo;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JokesApiTests extends ApiTestBase {    

    @Test
    public void getJokesCategoryTest() {
        createTestCase("Validate list of Jokes Category using '/categories' endpoint.", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            List<String> listOfCategories = new ArrayList<>(Arrays.asList("Any", "Misc", "Programming", "Dark", "Pun", "Spooky", "Christmas"));
            List<JokeCategoryAlias> listOfCategoryAliases = new ArrayList<>(
                    List.of(
                            new JokeCategoryAlias("Miscellaneous","Misc"),
                            new JokeCategoryAlias("Coding","Programming"),
                            new JokeCategoryAlias("Development","Programming"),
                            new JokeCategoryAlias("Halloween","Spooky")
                    ));

            testStep("1. Send '/categories' api endpoint request. Check status code '200' and categories list.", () -> {
                List<String> actualCategoriesList = service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_CATEGORIES)
                        .validateStatusCode(200)
                        .extractJsonPath().getList("categories");

                softAssert.assertEquals(actualCategoriesList, listOfCategories, "Joke categories list does not match.");
            });

            testStep("2. Send '/categories' api endpoint request. Check status code '200' and category aliases.", () -> {
                List<JokeCategoryAlias> actualCategoryAliases = service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_CATEGORIES)
                        .validateStatusCode(200)
                        .extractJsonPath().getList("categoryAliases", JokeCategoryAlias.class);

                softAssert.assertEquals(actualCategoryAliases, listOfCategoryAliases, "Joke category alias list does not match.");
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void getJokesCategoryUsingJsonPathTest() {
        createTestCase("Validate list of Jokes Category using '/categories' endpoint.", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            final JsonPath[] jsonPath = new JsonPath[1];
            List<String> listOfCategories = new ArrayList<>(Arrays.asList("Any", "Misc", "Programming", "Dark", "Pun", "Spooky", "Christmas"));
            List<JokeCategoryAlias> listOfCategoryAliases = new ArrayList<>(
                    List.of(
                            new JokeCategoryAlias("Miscellaneous","Misc"),
                            new JokeCategoryAlias("Coding","Programming"),
                            new JokeCategoryAlias("Development","Programming"),
                            new JokeCategoryAlias("Halloween","Spooky")
                    ));

            testStep("1. Send '/categories' api endpoint request. Check status code '200'.", () -> {
                jsonPath[0] = service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_CATEGORIES)
                        .validateStatusCode(200)
                        .extractJsonPath();
            });

            testStep("2. Validate categories list from the request.", () -> {
                softAssert.assertEquals(jsonPath[0].getList("categories"),
                        listOfCategories,
                        "Joke category list does not match."
                );
            });

            testStep("3. Validate category aliases from the request.", () -> {
                softAssert.assertEquals(jsonPath[0].getList("categoryAliases", JokeCategoryAlias.class),
                        listOfCategoryAliases,
                        "Joke category alias list does not match."
                );
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void getJokesCategoryUsingAtomicReferenceTest() {
        createTestCase("Validate list of Jokes Category using '/categories' endpoint.", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            AtomicReference<JsonPath> jsonPathRef = new AtomicReference<>();
            List<String> listOfCategories = new ArrayList<>(Arrays.asList("Any", "Misc", "Programming", "Dark", "Pun", "Spooky", "Christmas"));
            List<JokeCategoryAlias> listOfCategoryAliases = new ArrayList<>(
                    List.of(
                            new JokeCategoryAlias("Miscellaneous","Misc"),
                            new JokeCategoryAlias("Coding","Programming"),
                            new JokeCategoryAlias("Development","Programming"),
                            new JokeCategoryAlias("Halloween","Spooky")
                    ));

            testStep("1. Send '/categories' api endpoint request. Check status code '200'.", () -> {
                jsonPathRef.set(service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_CATEGORIES)
                        .validateStatusCode(200)
                        .extractJsonPath()
                );
            });

            testStep("2. Validate categories list from the request.", () -> {
                softAssert.assertEquals(jsonPathRef.get().getList("categories"),
                        listOfCategories,
                        "Joke category list does not match."
                );
            });

            testStep("3. Validate category aliases from the request.", () -> {
                softAssert.assertEquals(jsonPathRef.get().getList("categoryAliases", JokeCategoryAlias.class),
                        listOfCategoryAliases,
                        "Joke category alias list does not match."
                );
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void multipleAssertionFromSingleRequestTest() {
        createTestCase("Validate list of Jokes Category using '/categories' endpoint.", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            List<String> listOfCategories = new ArrayList<>(Arrays.asList("Any", "Misc", "Programming", "Dark", "Pun", "Spooky", "Christmas"));
            List<JokeCategoryAlias> listOfCategoryAliases = new ArrayList<>(
                    List.of(
                            new JokeCategoryAlias("Miscellaneous","Misc"),
                            new JokeCategoryAlias("Coding","Programming"),
                            new JokeCategoryAlias("Development","Programming"),
                            new JokeCategoryAlias("Halloween","Spooky")
                    ));

            testStep("1. Send '/categories' api endpoint request. Check status code '200', categories and category aliases.", () -> {
                JsonPath jsonPath = service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_CATEGORIES)
                        .validateStatusCode(200)
                        .extractJsonPath();

                softAssert.assertEquals(jsonPath.getList("categories"), listOfCategories, "Joke categories list does not match.");
                softAssert.assertEquals(jsonPath.getList("categoryAliases", JokeCategoryAlias.class), listOfCategoryAliases, "Joke category alias list does not match.");
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void getJokeApiInfoTest() {
        createTestCase("Verify Joke api info using 'info' endpoint request.", () -> {
            AtomicReference<Response> response = new AtomicReference<>();
            testStep("1. Send 'info' endpoint request and check status code '200' and schema.", () -> {
                response.set(service()
                        .setBaseUri(JokesApiEndpoints.JOKES_BASE_URI)
                        .sendGetRequest(JokesApiEndpoints.JOKE_DEV_INFO)
                        .validateStatusCode(200)
                        .getResponse());

                response.get().then().body(matchesJsonSchemaInClasspath("jokesapi-schemas/joke_dev_info_schema.json"));
            });

            testStep("2. Validate joke api content from 'info' endpoint.", () -> {
                JokeDevInfo jokeBody = response.get().as(JokeDevInfo.class);

                loggerService.info("Joke API Info: " + jokeBody.info);
                loggerService.info("Joke API Version: " + jokeBody.version);
                loggerService.info("Joke API Total Jokes Count: " + jokeBody.jokes);
                loggerService.info("Joke API Supported Formats: " + jokeBody.formats);
                loggerService.info("Joke API Joke Languages: " + jokeBody.jokeLanguages);
                loggerService.info("Joke API System Languages: " + jokeBody.systemLanguages);
                loggerService.info("Joke API Timestamp: " + jokeBody.timestamp);
                loggerService.info("Joke API Error: " + jokeBody.error);
                loggerService.info("Joke Info: " + jokeBody.info);
                loggerService.info("Joke Categories: " + jokeBody.jokes.categories);
                loggerService.info("Joke Flags: " + jokeBody.jokes.flags);
                loggerService.info("Joke Types: " + jokeBody.jokes.types);
                loggerService.info("Joke Submission URL: " + jokeBody.jokes.submissionUrl);
                loggerService.info("Joke ID Range: " + jokeBody.jokes.idRange);
                loggerService.info("Joke Safe Jokes: " + jokeBody.jokes.safeJokes);
            });
        });
    }
}
