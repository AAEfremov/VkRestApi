package core;

import java.lang.reflect.Type;
import java.util.HashMap;

import java.util.List;
import java.util.Random;


import beans.VkGetCitiesAnswer;
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

import static core.VkMethodsConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class VkApiMethods {

    //builder pattern
    private VkApiMethods(){
    }

    //Vk Api methods parameters
    private HashMap<String,Integer> params = new HashMap<>();
    private HashMap<String, String> sparams = new HashMap<>();

    //Builder class for GetRegions method
    public static class RegionsApiBuilder {
        VkApiMethods getRegionsApi;

        private RegionsApiBuilder(VkApiMethods gcApi){
            getRegionsApi = gcApi;
        }

        public RegionsApiBuilder country_id(int country_id){
            getRegionsApi.params.put(PARAM_COUNTRY_ID, country_id);
            return this;
        }

        public RegionsApiBuilder search_query(String query){
            getRegionsApi.sparams.put(PARAM_SEARCH_QUERY, query);
            return this;
        }

        public RegionsApiBuilder offset(int offset){
            getRegionsApi.params.put(PARAM_OFFSET, offset);
            return this;
        }

        public RegionsApiBuilder count(int count){
            getRegionsApi.params.put(PARAM_COUNT, count);
            return this;
        }

        public RegionsApiBuilder version(String version) {
            getRegionsApi.sparams.put(PARAM_VERSION, version);
            return this;
        }

        public Response callApi(){
            return RestAssured.with()
                    .queryParams(getRegionsApi.params)
                    .queryParams(getRegionsApi.sparams)
                    .log().all()
                    .get(VK_API_REGIONS_URI).prettyPeek();
        }
    }

    //Builder constructor for GetRegions method
    public static RegionsApiBuilder withForRegions(){
        VkApiMethods api = new VkApiMethods();
        return new RegionsApiBuilder(api);
    }

    //Builder class for GetCities method
    public static class CitiesApiBuilder {

        VkApiMethods getCitiesApi;

        private CitiesApiBuilder(VkApiMethods gcApi){
            getCitiesApi = gcApi;
        }

        public CitiesApiBuilder country_id(int country_id){
            getCitiesApi.params.put(PARAM_COUNTRY_ID, country_id);
            return this;
        }

        public CitiesApiBuilder region_id(int region_id){
            getCitiesApi.params.put(PARAM_REGION_ID, region_id);
            return this;
        }

        public CitiesApiBuilder search_query(String query){
            getCitiesApi.sparams.put(PARAM_SEARCH_QUERY, query);
            return this;
        }
        public CitiesApiBuilder need_all(Integer flag){
            getCitiesApi.params.put(PARAM_NEED_ALL, flag);
            return this;
        }

        public CitiesApiBuilder offset(int offset){
            getCitiesApi.params.put(PARAM_OFFSET, offset);
            return this;
        }

        public CitiesApiBuilder count(int count){
            getCitiesApi.params.put(PARAM_COUNT, count);
            return this;
        }

        public CitiesApiBuilder version(String version) {
            getCitiesApi.sparams.put(PARAM_VERSION, version);
            return this;
        }

        public Response callApi(){
            return RestAssured.with()
                    .queryParams(getCitiesApi.params)
                    .queryParams(getCitiesApi.sparams)
                    .log().all()
                    .get(VK_API_CITIES_URI).prettyPeek();
        }
    }

    //Builder constructor for GetCities method
    public static CitiesApiBuilder withForCities() {
        VkApiMethods api = new VkApiMethods();
        return new CitiesApiBuilder(api);
    }


    //set base request and response specifications to use in tests
    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration(String url){
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(url)
                .build();
    }

    //get response from request in json format
    public static List<VkGetRegionsAnswer> getVkGetRegionsAnswers(Response response) {

        String responseStr = response.print();
        int beginIndex = responseStr.indexOf("{", 13);
        int endIndex = responseStr.lastIndexOf("]");
        String jsonStr = "[" + responseStr.substring(beginIndex, endIndex) + "]";
        Type listType = new TypeToken<List<VkGetRegionsAnswer>>() {
        }.getType();
        return new Gson().fromJson(jsonStr, listType);
    }

    public static List<VkGetCitiesAnswer> getVkGetCitiesAnswers(Response response) {

        String responseStr = response.print();
        int beginIndex = responseStr.indexOf("{", 13);
        int endIndex = responseStr.lastIndexOf("]");
        String jsonStr = "[" + responseStr.substring(beginIndex, endIndex) + "]";
        Type listType = new TypeToken<List<VkGetCitiesAnswer>>() {
        }.getType();
        return new Gson().fromJson(jsonStr, listType);
    }
}