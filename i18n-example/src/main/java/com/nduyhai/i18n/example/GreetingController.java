package com.nduyhai.i18n.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/greeting"))
public class GreetingController {

    @GetMapping
    public void hello() {
        throw new GreetingBusinessException(GreetingErrorCode.INVALID_REQUEST);

    }

    @PostMapping
    public void create() {
        throw new GreetingBusinessException(GreetingErrorCode.INTERNAL_SERVER_ERROR);

    }


}
