package com.harsh.securebank.controller;

import java.util.List;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/prefilter")
    @PreFilter("filterObject.length() >= 5")
    public List<String> preFilterApiTest(@RequestBody List<String> names)
    {
        System.out.println("Pre Filtered --> Size of names : " + names.size());
        return names;
    }

    @PostMapping("/postfilter")
    @PostFilter("filterObject.length() >= 5")
    public List<String> postFilterApiTest(@RequestBody List<String> names)
    {
        System.out.println("Post Filtered --> Size of names : " + names.size());
        return names;
    }
}
