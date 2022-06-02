package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.triofantastico.practiceproject.httpclient.restful.GraphqlClient;
import com.triofantastico.practiceproject.model.gql.Company;
import com.triofantastico.practiceproject.model.gql.GraphQLQuery;
import com.triofantastico.practiceproject.model.gql.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class GqlTest {

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
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/fetchCompanyDetails.graphql");
        ObjectNode variables = new ObjectMapper().createObjectNode();

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphPayload);
        JsonNode node = objectMapper.readTree(response.getBody().asString());
        JsonNode coordinatesNode = node.at("/data/company");

        Company companyDetails = objectMapper.treeToValue(coordinatesNode, Company.class);

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
        final ArrayList<String> expRes = new ArrayList<>(Arrays.asList(
                "Thaicom 6",
                "AsiaSat 6",
                "OG-2 Mission 2",
                "FalconSat",
                "CRS-1",
                "CASSIOPE",
                "ABS-3A / Eutelsat 115W B",
                "COTS 1",
                "TürkmenÄlem 52°E / MonacoSAT",
                "CRS-11"
        )
        );

        List<String> listOfMissionNames = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/retrieveTenLaunches.graphql");
        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("limit", LIMIT);

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphqlPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphqlPayload);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonNode node = objectMapper.readTree(response.getBody().asString());
        ArrayNode coordinatesNode = (ArrayNode) node.at("/data/launches");
        for (JsonNode nod : coordinatesNode) {
            listOfMissionNames.add(nod.get("mission_name").asText());
        }

        assertEquals(expRes, listOfMissionNames);
    }

    @Test
    void check_data_from_valid_random_added_user() throws IOException {
        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("src/test/resources/insertUsers.graphql");

        User user = User.createValidRandomUser();

        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("id", String.valueOf(user.getId()));
        variables.put("name", user.getName());
        variables.put("rocket", user.getRocket());

        GraphqlClient graphqlClient = new GraphqlClient();
        String graphqlPayload = graphqlClient.parseGraphql(file, variables);

        // ACT
        Response response = graphqlClient.send(graphqlPayload);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        JsonNode responseNode = objectMapper.readTree(response.getBody().asString());
        ArrayNode coordinatesNode = (ArrayNode) responseNode.at("/data/insert_users/returning");

        ArrayList<User> returningUsersList = objectMapper.treeToValue(coordinatesNode, User.class);

        int lastElement = returningUsersList.size() - 1;
        for (int i = 0; i < returningUsersList.size(); i++) {
            Assertions.assertAll("User object should be handled as itended",
                    () -> assertEquals(String.valueOf(user.getId()), String.valueOf(returningUsersList.get(lastElement).getId())),
                    () -> assertEquals(user.getName(), returningUsersList.get(lastElement).getName()),
                    () -> assertEquals(user.getRocket(), returningUsersList.get(lastElement).getRocket()));
        }
    }

}
