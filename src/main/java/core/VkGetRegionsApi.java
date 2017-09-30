package core;

import java.lang.reflect.Type;
import java.util.HashMap;

import java.util.List;
import java.util.Random;


import beans.VkGetRegionsAnswer;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static core.VkGetRegionsConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class VkGetRegionsApi {

    //builder pattern
    private VkGetRegionsApi(){
    }

    private HashMap<String,Integer> params = new HashMap<>();
    private HashMap<String, String> sparams = new HashMap<>();

    public static class ApiBuilder {
        VkGetRegionsApi getRegionsApi;

        private ApiBuilder(VkGetRegionsApi gcApi){
            getRegionsApi = gcApi;
        }

        public ApiBuilder country_id(int country_id){
            getRegionsApi.params.put(PARAM_COUNTRY_ID, country_id);
            return this;
        }

        public ApiBuilder search_query(String query){
            getRegionsApi.sparams.put(PARAM_SEARCH_QUERY, query);
            return this;
        }

        public ApiBuilder offset(int offset){
            getRegionsApi.params.put(PARAM_OFFSET, offset);
            return this;
        }

        public ApiBuilder count(int count){
            getRegionsApi.params.put(PARAM_COUNT, count);
            return this;
        }

        public ApiBuilder version(String version) {
            getRegionsApi.sparams.put(PARAM_VERSION, version);
            return this;
        }

        public Response callApi(){
            return RestAssured.with()
                    .queryParams(getRegionsApi.params)
                    .queryParams(getRegionsApi.sparams)
                    .log().all()
                    .get(VK_API_URI).prettyPeek();
        }
    }

    public static ApiBuilder with(){
        VkGetRegionsApi api = new VkGetRegionsApi();
        return new ApiBuilder(api);
    }

    //get ready Speller answers list form api response



    //set base request and response specifications to use in tests
    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration(){
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(VK_API_URI)
                .build();
    }

    public static List<VkGetRegionsAnswer> getVkGetRegionsAnswers(Response response) {

        String responseStr = response.print();
        int beginIndex = responseStr.indexOf("{", 13);
        int endIndex = responseStr.lastIndexOf("]");
        String jsonStr = "[" + responseStr.substring(beginIndex, endIndex) + "]";
        Type listType = new TypeToken<List<VkGetRegionsAnswer>>() {
        }.getType();
        List<VkGetRegionsAnswer> list = new Gson().fromJson(jsonStr, listType);
        return list;

    }
}