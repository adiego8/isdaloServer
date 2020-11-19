package com.tutorial.services;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MultiServices {

    //Properties 
    RestTemplate httpRequest;

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
