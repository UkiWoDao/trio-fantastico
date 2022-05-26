package com.triofantastico.practiceproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triofantastico.practiceproject.httpclient.restful.GraphqlClient;
import com.triofantastico.practiceproject.model.gql.Data;
import com.triofantastico.practiceproject.model.gql.GraphQLQuery;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

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
    void getCompanyData_checkCeo_shouldBeElonMusk_through_jackson() throws JsonProcessingException {

        // ARRANGE
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        GraphQLQuery query = new GraphQLQuery();
        query.setQuery("{\"query\":\"{\\n  company {\\n    name\\n    ceo\\n    coo\\n  }\\n}\",\"variables\":null}");

        GraphqlClient graphqlClient = new GraphqlClient();

        // ACT
        Response jsonQuery = graphqlClient.create(query.getQuery());
        Data gqlJson = objectMapper.readValue(jsonQuery.getBody().asString(), Data.class);

        // ASSERT
        assertEquals(HttpStatus.SC_OK, jsonQuery.getStatusCode());
        assertEquals("SpaceX", gqlJson.getData().get("company").get(0).getName());
        assertEquals("Elon Musk", gqlJson.getData().get("company").get(0).getCeo());
        assertEquals("Gwynne Shotwell", gqlJson.getData().get("company").get(0).getCoo());
    }
}
