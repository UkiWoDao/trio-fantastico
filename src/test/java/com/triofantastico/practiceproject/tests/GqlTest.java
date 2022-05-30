package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.triofantastico.practiceproject.httpclient.restful.GraphqlClient;
import com.triofantastico.practiceproject.model.gql.Company;
import com.triofantastico.practiceproject.model.gql.GraphQLQuery;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Response response = graphqlClient.create(graphPayload);
        JsonNode node = objectMapper.readTree(response.getBody().asString());
        JsonNode coordinatesNode = node.at("/data/company");

        Company companyDetails = objectMapper.treeToValue(coordinatesNode, Company.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("SpaceX", companyDetails.getName());
        assertEquals("Elon Musk", companyDetails.getCeo());
        assertEquals("Gwynne Shotwell", companyDetails.getCoo());
    }
}
