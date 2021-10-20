package com.ebki.controller;

import com.ebki.service.CheckInService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebik/checkin/**")
@CrossOrigin("*")
public class CheckInController {

    private final CheckInService check;

    public CheckInController(CheckInService check) {
        this.check = check;
    }
}
