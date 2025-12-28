package org;

import org.framework.base.ApiTestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Example test class demonstrating the enhanced ServiceBase with integrated logging.
 * All API calls are automatically logged to console, file, and Extent reports.
 *
 * Usage:
 * - Extend this class in your actual test classes
 * - Use the service object for all API calls
 * - All logging is automatic via LoggerService
 */
public class APITestExample extends ApiTestBase {

    @BeforeMethod
    public void setUp() {
       service().setBaseUri("https://jsonplaceholder.typicode.com").setJsonContentType();
    }

    /**
     * Example 1: Simple GET request with logging
     * Demonstrates:
     * - Setting base URI
     * - Executing GET request
     * - Validating status code
     * - All operations logged automatically
     */
    @Test
    public void testSimpleGetRequest() {
        service().sendGetRequest("/posts/1")
               .validateStatusCode(200)
               .validateResponseNotEmpty()
               .validateContentType("application/json");

        // All of the above generates logs:
        // Console: Visible in real-time
        // File: Written to log file via Log4j2
        // Extent: Added to HTML report
    }

    /**
     * Example 2: GET request with query parameters
     * Demonstrates:
     * - Adding multiple query parameters
     * - Parameter logging
     * - Response extraction
     */
    @Test
    public void testGetWithQueryParameters() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("_page", "1");
        queryParams.put("_limit", "5");

        service().addQueryParams(queryParams)  // Logs each parameter
               .sendGetRequest("/posts")
               .validateStatusCode(200)
               .validateResponseContains("userId");

        // Logged: "Adding 2 query parameters"
        // Logged: "Query Param: _page = 1"
        // Logged: "Query Param: _limit = 5"
        // Logged: "Full URL: https://jsonplaceholder.typicode.com/posts?_page=1&_limit=5"
    }

    /**
     * Example 3: POST request with body and headers
     * Demonstrates:
     * - Setting content type
     * - Adding custom headers
     * - Setting request body
     * - Validating response
     */
    @Test
    public void testPostWithBodyAndHeaders() {
        String payload = "{\"title\":\"Test Post\",\"body\":\"This is a test\",\"userId\":1}";

        service().setJsonContentType()  // Logs content type
               .addHeader("X-Custom-Header", "TestValue")  // Logs header
               .setBody(payload)  // Logs body
               .sendPostRequest("/posts")
               .validateStatusCode(201)
               .validateResponseContains("id")
               .validateResponseNotEmpty();

        // Logged: "Setting Content-Type: application/json"
        // Logged: "Adding Header: X-Custom-Header = TestValue"
        // Logged: "Setting Request Body: {...}"
        // Logged: Complete response details
    }

    /**
     * Example 4: PUT request with path parameters
     * Demonstrates:
     * - Adding path parameters
     * - PUT execution
     * - Response content validation
     */
    @Test
    public void testPutWithPathParameters() {
        String updatedPayload = "{\"title\":\"Updated Title\",\"body\":\"Updated body\",\"userId\":1}";

        service().addPathParam("postId", "1")  // Logs path parameter
               .setBody(updatedPayload)
               .sendPutRequest("/posts/{postId}")
               .validateStatusCode(200)
               .validateResponseContains("Updated Title");

        // Logged: "Adding Path Parameter: postId = 1"
        // Logged: "Executing PUT request to endpoint: /posts/{postId}"
        // Logged: "Full URL: https://jsonplaceholder.typicode.com/posts/1"
    }

    /**
     * Example 5: PATCH request
     * Demonstrates:
     * - Partial updates with PATCH
     * - Minimal payload
     * - Validation chaining
     */
    @Test
    public void testPatchRequest() {
        String patchPayload = "{\"title\":\"Partially Updated\"}";

        service().addPathParam("postId", "1")
               .setBody(patchPayload)
               .sendPatchRequest("/posts/{postId}")
               .validateStatusCode(200);

        // Logged: Patch request execution with body
    }

    /**
     * Example 6: DELETE request
     * Demonstrates:
     * - DELETE method execution
     * - Empty response validation
     */
    @Test
    public void testDeleteRequest() {
        service().addPathParam("postId", "1")
               .sendDeleteRequest("/posts/{postId}")
               .validateStatusCode(200);

        // Logged: DELETE request execution details
    }

    /**
     * Example 7: Response extraction with JSONPath
     * Demonstrates:
     * - Extracting values from response
     * - Using extracted values in assertions or subsequent requests
     * - Automatic logging of extracted data
     */
    @Test
    public void testResponseExtraction() {
        service().sendGetRequest("/posts/1")
               .validateStatusCode(200);

        // Extract single value
        Object userId = service().extractFromResponse("userId");

        // Extract as String
        String title = service().extractStringFromResponse("title");

        // Extract as Integer
        Integer postId = service().extractIntegerFromResponse("id");

        // Use extracted data in subsequent operations
        String userIdStr = userId.toString();
        service().clearRequestSpec()
               .addPathParam("userId", userIdStr)
               .sendGetRequest("/users/{userId}")
               .validateStatusCode(200);

        // Each extraction is logged with the extracted value
    }

    /**
     * Example 8: Authentication patterns
     * Demonstrates:
     * - Basic authentication
     * - Bearer token authentication
     * - Header-based custom authentication
     */
    @Test
    public void testAuthenticationPatterns() {
        // Pattern 1: Basic Auth (commented - for demonstration)
        /*
        service.setBasicAuth("username", "password")
               .sendGetRequest("/secure-endpoint")
               .validateStatusCode(200);
        // Logged: "Setting Basic Authentication for user: username"
        */

        // Pattern 2: Bearer Token (commented - for demonstration)
        /*
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
        service.setBearerToken(token)
               .sendGetRequest("/protected")
               .validateStatusCode(200);
        // Logged: "Setting Bearer Token Authentication"
        // Logged: "Adding Header: Authorization = Bearer <token>"
        */

        // Pattern 3: Custom Header Auth
        service().addHeader("X-API-Key", "api-key-12345")
               .addHeader("X-Auth-Token", "auth-token-xyz")
               .sendGetRequest("/posts")
               .validateStatusCode(200);
        // Logged: Each header addition with values
    }

    /**
     * Example 9: Method chaining with validation
     * Demonstrates:
     * - Complete fluent API usage
     * - All validations in a single chain
     * - Multiple validation types
     */
    @Test
    public void testCompleteMethodChaining() {
        String payload = "{\"title\":\"Chained Request\",\"body\":\"Test body\",\"userId\":1}";

        service().addHeader("X-Test", "Value")
               .setBody(payload)
               .sendPostRequest("/posts")
               .validateStatusCode(201)
               .validateResponseContains("id")
               .validateResponseContains("title")
               .validateResponseNotEmpty()
               .validateContentType("application/json");

        // All of these operations are logged in sequence
        // Each log entry captures the operation and result
    }

    /**
     * Example 10: Error handling and logging
     * Demonstrates:
     * - How validation failures are logged
     * - Error details in console, file, and Extent reports
     */
    @Test(expectedExceptions = AssertionError.class)
    public void testValidationFailureLogging() {
        service().sendGetRequest("/posts/1")
               .validateStatusCode(404);  // This will fail with 200

        // Logged: "Status Code Validation Failed: Expected 404 but got 200"
        // This error appears in:
        // - Console output
        // - Log file
        // - Extent report (marked as FAILED)
    }

    /**
     * Example 11: Multiple headers setup
     * Demonstrates:
     * - Adding multiple headers at once
     * - Bulk parameter logging
     */
    @Test
    public void testMultipleHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("X-Custom-1", "Value1");
        headers.put("X-Custom-2", "Value2");

        service().addHeaders(headers)  // Logs all 4 headers
               .sendGetRequest("/posts")
               .validateStatusCode(200);

        // Logged: "Adding 4 headers"
        // Logged: "Header: Content-Type = application/json"
        // Logged: "Header: Accept = application/json"
        // etc.
    }

    /**
     * Example 12: Reusing service instance across requests
     * Demonstrates:
     * - Multiple requests in sequence
     * - Clear specification between requests
     * - Logging continuity
     */
    @Test
    public void testMultipleSequentialRequests() {
        // First request: Get all posts
        service().addQueryParam("_limit", "1")
               .sendGetRequest("/posts")
               .validateStatusCode(200);

        String firstPostId = service().extractStringFromResponse("[0].id");

        // Clear for next request
        service().clearRequestSpec();

        // Second request: Get specific post
        service().addPathParam("postId", firstPostId)
               .sendGetRequest("/posts/{postId}")
               .validateStatusCode(200);

        // Clear for third request
        service().clearRequestSpec();

        // Third request: Get comments for post
        service().addPathParam("postId", firstPostId)
               .sendGetRequest("/posts/{postId}/comments")
               .validateStatusCode(200)
               .validateResponseNotEmpty();

        // Each request is fully logged with context
    }

    /**
     * Example 13: Response header extraction
     * Demonstrates:
     * - Getting response headers
     * - Header logging
     */
    @Test
    public void testResponseHeaderExtraction() {
        service().sendGetRequest("/posts")
               .validateStatusCode(200);

        // Extract specific headers
        String contentType = service().getResponseHeader("Content-Type");
        String contentLength = service().getResponseHeader("Content-Length");

        // Logged: "Getting Response Header: Content-Type"
        // Logged: "Response Header Content-Type = application/json; charset=utf-8"

        // Use headers for assertions or logging
        assert contentType != null : "Content-Type header is missing";
    }

    /**
     * Example 14: Full request details logging
     * Demonstrates:
     * - Logging request details before execution
     * - Debugging helper method
     */
    @Test
    public void testRequestDetailsLogging() {
        service().setBaseUri("https://api.example.com")
               .addQueryParam("key", "value")
               .logRequestDetails();  // Log current configuration

        // Logged: "====== REQUEST DETAILS ======"
        // Logged: "Method: GET/POST/PUT/PATCH/DELETE (Will be set on execution)"
        // Logged: "Base URI: https://api.example.com"
        // etc.
    }

    /**
     * Example 15: GET request with Basic Authentication
     * Demonstrates:
     * - Using setBasicAuth for Basic Authentication
     * - Automatic logging of auth details
     */
    @Test
    public void testGetWithBasicAuth() {
        service().setBaseUri("https://httpbin.org")
               .setBasicAuth("user", "passwd")
               .sendGetRequest("/basic-auth/user/passwd")
               .validateStatusCode(200)
               .validateResponseContains("authenticated");

        // Logged: "Setting Basic Authentication for user: user"
        // Logged: "Executing GET request to endpoint: /basic-auth/user/passwd"
    }

    /**
     * Example 16: GET request with OAuth2 Bearer Token Authentication
     * Demonstrates:
     * - Using setOAuth2Token for Bearer Token Authentication
     * - Automatic logging of token usage
     */
    @Test
    public void testGetWithOAuth2Token() {
        String fakeToken = "your-oauth2-token-here"; // Replace with a real token for real tests
        service().setBaseUri("https://httpbin.org")
               .setOAuth2Token(fakeToken)
               .sendGetRequest("/bearer")
               .validateStatusCode(200);

        // Logged: "Setting OAuth2 Bearer Token Authentication"
        // Logged: "Adding Header: Authorization = Bearer <token>"
    }
}
