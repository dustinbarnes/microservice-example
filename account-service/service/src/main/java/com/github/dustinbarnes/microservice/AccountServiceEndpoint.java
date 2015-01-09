package com.github.dustinbarnes.microservice;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/{user}/account", produces= MediaType.APPLICATION_JSON_VALUE)
public class AccountServiceEndpoint {

    @Autowired
    private WebServiceScatterGather scatterGather;

    @RequestMapping(method= RequestMethod.GET, value="/async")
    public ResponseEntity<Map<String, Object>> getAccountAsync(@PathVariable @NotEmpty String user)
    {
        Map<String, Object> response = scatterGather.getAccount(user);
        response.put("user", user);
        response.put("password", "password123");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.GET, value="/sync")
    public ResponseEntity<Map<String, Object>> getAccountSync(@PathVariable @NotEmpty String user)
    {
        Map<String, Object> response = scatterGather.getAccountSync(user);
        response.put("user", user);
        response.put("password", "password123");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
