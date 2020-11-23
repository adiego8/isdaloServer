package com.tutorial.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tutorial.domains.Document;
import com.tutorial.services.MultiServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

@RestController
@EnableCaching
public class ComunicationController {

    //Autowiring the service
    @Autowired
    MultiServices multiServices;

    /**
     * This is the initial path / representing that all connections were successfully completed
     */
    @GetMapping
    public Map<String,Object> testConnection(){
        //Parse the data to Json Object
        JsonParser parser = JsonParserFactory.getJsonParser();
        try {
            //Third Party API Connection
            String result = multiServices.getHttpRequest().getForEntity( /*this has to be an environment variable*/ "https://api.case.law/v1/cases/?search=baronetcy",String.class).getBody();
            //Parse the Result to a Map
            Map<String,Object> parsedResult = parser.parseMap(result);
            return parsedResult;
        } catch (RestClientException e) {
            return parser.parseMap(e.getMessage());
        }
    }


    /**
     * Get the words from the parameters in the route to contact the third party API
     * when contacted cached save the information in Redis.
     * Those values are the search query parameters.
     */
    @GetMapping
    @RequestMapping("/{words}/{id}") //Getting words and ids from the path, the correct method to use
                                     //in this case would be a POST request since we are saving data in Redis
    public String queryByWords(@PathVariable("words") String words,@PathVariable("id") int id){

        // Check if the query already exists in Redis meaning check if it is already cached therefore we need to
        // create some sort of bridge where there is not need to get to the point where we need to contact the
        // third party API, but first we need to test that the data is going to be saved correctly in Redis

        //First create the url to request the variables to
        String hostName = "https://api.case.law/v1/cases/?search=";

        //Clean up the query words and place it in the format "word1+word2+..." but for testing purposes will not
        //fix the wording so we are going to use only one word and its gonna be words variable passed as parameter


        //Second create the actual query using the words passed as parameters
        String url = hostName + words;

        String result = multiServices.getHttpRequest().getForEntity(url,String.class).getBody();
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String,Object> parsedResult =  jsonParser.parseMap(result);

        ArrayList<Map<String,Object>> results = (ArrayList<Map<String, Object>>) parsedResult.get("results");

        //Creating the document that is going to be saved
        Document doc = new Document();
        doc.setId(id);
        doc.setCaseName((String) results.get(0).get("name"));
        doc.setCaseUrlToPdf((String) results.get(0).get("frontend_pdf_url"));
        doc.setWordsQuery(words);

        multiServices.documentRepository.save(doc);

        return doc.toString();
    }



    /**
     * Get all queries cached in Redis Database that in this case is going to be only the first document returned
     * from the query of the word restaurant and we returned as a String
     **/
    @GetMapping("/all")
    public List<Object> getAllDocuments(){
        return multiServices.documentRepository.findAll();
    }



    /**
     * Clear all data in Redis Database IMPORTANT find out how to do this since I didn't find any method to do this task
     */
    @DeleteMapping("/delete/{id}")
    @CacheEvict(key = "#id",value = "Document")
    public String deleteRecordById(@PathVariable int id){
        return multiServices.documentRepository.deleteDocumentById(id);
    }


    /**
     * Redis caching by ID
     */
    @GetMapping("/find/{id}")
    //This is the tool to cached the information we can add caching conditions in this method
    @Cacheable(key = "#id", value = "Document")
    public Document findSearch(@PathVariable int id ){
        return multiServices.documentRepository.findDocumentById(id);
    }



    /**-------------- Endpoints for information that is going to be saved in POSTGRES ---------------*/

    //Missing set the connection with Postgres
    //Place microservice in a docker container
    //Create unit test for this controller and services with a CI/CD





}
