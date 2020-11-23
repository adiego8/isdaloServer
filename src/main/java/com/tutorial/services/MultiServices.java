package com.tutorial.services;
import com.tutorial.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MultiServices {

    //Properties 
    RestTemplate httpRequest;

    //Autowire the Respository for the documents data
    @Autowired
    public DocumentRepository documentRepository;

    //Service default constructor
    MultiServices(){   
        this.httpRequest = new RestTemplate();     
    }

    public RestTemplate getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(RestTemplate httpRequest) {
        this.httpRequest = httpRequest;
    }
    

}
