package dev.toaster.thanos.controllers;

import dev.toaster.thanos.dtos.DeregisterServerDTO;
import dev.toaster.thanos.dtos.RegisterServerDTO;
import dev.toaster.thanos.servers.Server;
import dev.toaster.thanos.services.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class ServersController {
    @Autowired
    private LoadBalancerService loadBalancerService;

    @GetMapping("/registered-servers")
    public ResponseEntity<Map<String, Server>> getRegisteredServers() {
        return ResponseEntity.ok(this.loadBalancerService.getRegisteredServers());
    }

    @GetMapping("/healthy-servers")
    public ResponseEntity<Map<String, Server>> getHealthyServers() {
        return ResponseEntity.ok(this.loadBalancerService.getHealthyServers());
    }

    @PostMapping("/register-server")
    public ResponseEntity<String> registerServer(@RequestBody RegisterServerDTO registerServerDTO) {
        return this.loadBalancerService.registerServer(registerServerDTO);
    }

    @PostMapping("/deregister-server")
    public ResponseEntity<String> deregisteredServer(@RequestBody DeregisterServerDTO deregisterServerDTO) {
        return this.loadBalancerService.deregisterServer(deregisterServerDTO);
    }
}
