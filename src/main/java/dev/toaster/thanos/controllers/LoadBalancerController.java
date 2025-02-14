package dev.toaster.thanos.controllers;

import dev.toaster.thanos.services.LoadBalancerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/")
public class LoadBalancerController {
    @Autowired
    private LoadBalancerService loadBalancerService;


    @RequestMapping("/**")
    public ResponseEntity<Object> forwardRequest(HttpServletRequest request) throws IOException, URISyntaxException {
        return this.loadBalancerService.forwardRequest(request);
    }
}
