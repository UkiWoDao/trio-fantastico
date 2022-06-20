package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.triofantastico.practiceproject.httpclient.restful.GraphqlClient;
import com.triofantastico.practiceproject.model.gql.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class GqlTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String GRAPHQL_FILE_PACKAGE_PATH = "src/test/resources/graphql/";

    @Test
    void getCompanyData_checkCeo_shouldBeElonMusk() {
        GraphQLQuery query = new GraphQLQuery();
        query.setQuery("{ company { name ceo coo } }");

        given().
                contentType(ContentType.JSON).
                body(query).
        when().
                post("https://api.spacex.land/graphql/").
        then().
                assertThat().
                statusCode(200).
                and().
                body("data.company.ceo", equalTo("Elon Musk"));
    }

    @Test
    void getCompanyData_checkCeo_shouldBeElonMusk_through_jackson() throws IOException {
        // ARRANGE
        File file = new File(GRAPHQL_FILE_PACKAGE_PATH + "fetchCompanyDetails.graphql");
        ObjectNode variables = OBJECT_MAPPER.createObjectNode();

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphPayload);
        JsonNode node = OBJECT_MAPPER.readTree(response.getBody().asString());
        JsonNode coordinatesNode = node.at("/data/company");
        Company companyDetails = OBJECT_MAPPER.treeToValue(coordinatesNode, Company.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("SpaceX", companyDetails.getName());
        assertEquals("Elon Musk", companyDetails.getCeo());
        assertEquals("Gwynne Shotwell", companyDetails.getCoo());
    }

    @Test
    void getLaunches_checkMissionName() throws IOException {
        // ARRANGE
        final int LIMIT = 10;
        String nonAsciiRegex = "[^\\p{ASCII}]";
        final List<String> expectedOriginalMissionNames = List.of(
                "Thaicom 6",
                "AsiaSat 6",
                "OG-2 Mission 2",
                "FalconSat",
                "CRS-1",
                "CASSIOPE",
                "ABS-3A / Eutelsat 115W B",
                "COTS 1",
                "TürkmenÄlem 52°E / MonacoSAT",
                "CRS-11");

        List<String> expectedNormalizedMissionNames = new ArrayList<>();
        for (String missionName : expectedOriginalMissionNames) {
            Normalizer.normalize(missionName, Normalizer.Form.NFD);
            missionName = missionName.replaceAll(nonAsciiRegex, "");
            expectedNormalizedMissionNames.add(missionName);
        }

        List<String> actualNormalizedMissionNames = new ArrayList<>();
        File file = new File(GRAPHQL_FILE_PACKAGE_PATH + "retrieveLaunches.graphql");
        ObjectNode variables = OBJECT_MAPPER.createObjectNode();
        variables.put("limit", LIMIT);

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphqlPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphqlPayload);
        JsonNode responseNode = OBJECT_MAPPER.readTree(response.getBody().asString());

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        ArrayNode coordinatesNode = (ArrayNode) responseNode.at("/data/launches");

        for (JsonNode node : coordinatesNode) {
            String preNormalizedMissionName = node.get("mission_name").asText();
            Normalizer.normalize(preNormalizedMissionName, Normalizer.Form.NFD);
            String normalizedMissionName = preNormalizedMissionName.replaceAll(nonAsciiRegex, "");
            actualNormalizedMissionNames.add(normalizedMissionName);
        }

        assertEquals(expectedNormalizedMissionNames, actualNormalizedMissionNames);
    }

    @Test
    void check_data_from_valid_random_added_user() throws IOException {
        // ARRANGE
        File file = new File(GRAPHQL_FILE_PACKAGE_PATH + "insertUsers.graphql");

        User desiredUser = User.createValidRandomUser();

        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("id", String.valueOf(desiredUser.getId()));
        variables.put("name", desiredUser.getName());
        variables.put("rocket", desiredUser.getRocket());

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphqlPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphqlPayload);
        String responseBody = response.getBody().asString();
        int singleElement = 0;
        String jsonNodeString = OBJECT_MAPPER.readTree(responseBody).at("/data/insert_users/returning").get(singleElement).toString();
        User returnedUser = OBJECT_MAPPER.readValue(jsonNodeString, User.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(desiredUser, returnedUser);
    }

    @Test
    void getNumberOfShipsLaunces_checkDetails() throws IOException {
        // ARRANGE
        final int LIMIT = 3;
        Ship ship1 = new Ship("GO Ms Tree", "Port Canaveral", "https://i.imgur.com/MtEgYbY.jpg");
        Ship ship2 = new Ship("GO Ms Chief", "Port Canaveral", "https://imgur.com/NHsx95l.jpg");
        Ship ship3 = new Ship("Just Read The Instructions 2", "Port of Los Angeles", "https://i.imgur.com/7VMC0Gn.jpg");
        Ship ship4 = new Ship("GO Quest", "Port Canaveral", "https://i.imgur.com/ABXtHKa.jpg");
        Ship ship5 = new Ship("Of Course I Still Love You", "Port Canaveral", "https://i.imgur.com/28dCx6G.jpg");

        final List<Ship> expectedShipsResult = List.of(ship1, ship2, ship3, ship4, ship5);

        File file = new File(GRAPHQL_FILE_PACKAGE_PATH + "checkLimitOfShipsAndLaunches.graphql");
        ObjectNode variables = OBJECT_MAPPER.createObjectNode();
        variables.put("limit", LIMIT);

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphqlPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphqlPayload);
        String responseBody = response.getBody().asString();
        JsonNode launchesPastNode = OBJECT_MAPPER.readTree(responseBody).at("/data/launchesPast");

        ObjectReader reader = OBJECT_MAPPER.readerFor(new TypeReference<List<Ships>>() { });

        List<Ships> shipsList = reader.readValue(launchesPastNode);
        List<Ship> actualShipList = new ArrayList<>();

        for (Ships ships : shipsList) {
            actualShipList.addAll(ships.getShips());
        }
        System.out.println(actualShipList);

        // ASSERT
        assertTrue(actualShipList.containsAll(expectedShipsResult));
    }
}
