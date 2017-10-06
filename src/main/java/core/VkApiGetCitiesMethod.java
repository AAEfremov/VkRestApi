package core;

import beans.VkGetCitiesAnswer;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static core.VkMethodsConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class VkApiGetCitiesMethod {

    private VkApiGetCitiesMethod() {

    }

    //Vk Api methods parameters
    private HashMap<String,String> params = new HashMap<>();

    //Builder class for GetCities method
    public static class CitiesApiBuilder {

        VkApiGetCitiesMethod getCitiesApi;

        private CitiesApiBuilder(VkApiGetCitiesMethod gcApi){
            getCitiesApi = gcApi;
        }

        public CitiesApiBuilder country_id(Integer country_id){
            getCitiesApi.params.put(PARAM_COUNTRY_ID, String.valueOf(country_id));
            return this;
        }

        public CitiesApiBuilder region_id(Integer region_id){
            getCitiesApi.params.put(PARAM_REGION_ID, String.valueOf(region_id));
            return this;
        }

        public CitiesApiBuilder search_query(String query){
            getCitiesApi.params.put(PARAM_SEARCH_QUERY, query);
            return this;
        }
        public CitiesApiBuilder need_all(Integer flag){
            getCitiesApi.params.put(PARAM_NEED_ALL, String.valueOf(flag));
            return this;
        }

        public CitiesApiBuilder offset(Integer offset){
            getCitiesApi.params.put(PARAM_OFFSET, String.valueOf(offset));
            return this;
        }

        public CitiesApiBuilder count(Integer count){
            getCitiesApi.params.put(PARAM_COUNT, String.valueOf(count));
            return this;
        }

        public CitiesApiBuilder version(String version) {
            getCitiesApi.params.put(PARAM_VERSION, version);
            return this;
        }

        public Response callApi(){
            return RestAssured.with()
                    .queryParams(getCitiesApi.params)
                    .log().all()
                    .get(VK_API_CITIES_URI).prettyPeek();
        }
    }

    //Builder constructor for GetCities method
    public static CitiesApiBuilder with() {
        VkApiGetCitiesMethod api = new VkApiGetCitiesMethod();
        return new CitiesApiBuilder(api);
    }

    //set base request and response specifications to use in tests
    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration(String url){
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .setBaseUri(url)
                .build();
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
