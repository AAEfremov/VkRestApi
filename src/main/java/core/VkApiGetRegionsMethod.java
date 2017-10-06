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

import static core.VkMethodsConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class VkApiGetRegionsMethod {

    //builder pattern
    private VkApiGetRegionsMethod(){
    }

    //Vk Api methods parameters
    private HashMap<String,String> params = new HashMap<>();

    //Builder class for GetRegions method
    public static class RegionsApiBuilder {
        VkApiGetRegionsMethod getRegionsApi;

        private RegionsApiBuilder(VkApiGetRegionsMethod gcApi){
            getRegionsApi = gcApi;
        }

        public RegionsApiBuilder country_id(int country_id){
            getRegionsApi.params.put(PARAM_COUNTRY_ID, String.valueOf(country_id));
            return this;
        }

        public RegionsApiBuilder search_query(String query){
            getRegionsApi.params.put(PARAM_SEARCH_QUERY, query);
            return this;
        }

        public RegionsApiBuilder offset(int offset){
            getRegionsApi.params.put(PARAM_OFFSET, String.valueOf(offset));
            return this;
        }

        public RegionsApiBuilder count(int count){
            getRegionsApi.params.put(PARAM_COUNT, String.valueOf(count));
            return this;
        }

        public RegionsApiBuilder version(String version) {
            getRegionsApi.params.put(PARAM_VERSION, version);
            return this;
        }

        public Response callApi(){
            return RestAssured.with()
                    .queryParams(getRegionsApi.params)
                    .log().all()
                    .get(VK_API_REGIONS_URI).prettyPeek();
        }
    }

    //Builder constructor for GetRegions method
    public static RegionsApiBuilder with(){
        VkApiGetRegionsMethod api = new VkApiGetRegionsMethod();
        return new RegionsApiBuilder(api);
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
    //Gson умеет преобразовывать в обе стороны JSON -> объект класса и наоборот!
}