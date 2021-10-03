package com.ebki.controller;

import com.ebki.model.Driver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ebik/")
@CrossOrigin("*")
public class Controller {

    public void registerNewDriver(@RequestBody Driver driver) {

    }
}
