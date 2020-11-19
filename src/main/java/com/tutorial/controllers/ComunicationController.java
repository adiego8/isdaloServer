package com.tutorial.controllers;

import java.util.Map;

import com.tutorial.services.MultiServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
public class ComunicationController {

    //Autowiring the service
    @Autowired
    MultiServices multiServices;
    
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


}
