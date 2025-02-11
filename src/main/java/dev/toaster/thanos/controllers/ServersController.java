package dev.toaster.thanos.controllers;

import dev.toaster.thanos.dtos.DeregisterServerDTO;
import dev.toaster.thanos.dtos.RegisterServerDTO;
import dev.toaster.thanos.servers.Server;
import dev.toaster.thanos.services.ServersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class ServersController {
    @Autowired
    private ServersService serversService;

    @GetMapping("/registered-servers")
    public ResponseEntity<Map<String, Server>> getServers() {
        return ResponseEntity.ok(this.serversService.getServers());
    }

    @PostMapping("/register-server")
    public ResponseEntity<String> registerServer(@RequestBody RegisterServerDTO registerServerDTO) {
        if (this.serversService.registerServer(registerServerDTO)) {
            return ResponseEntity.ok("Registered: " + registerServerDTO.getHostname() + ":" + registerServerDTO.getPort());
        }
        return ResponseEntity.status(500).body("Error: Could not register server: " + registerServerDTO.getHostname() + ":" + registerServerDTO.getPort());
    }

    @PostMapping("/deregister-server")
    public ResponseEntity<String> deregisteredServer(@RequestBody DeregisterServerDTO deregisterServerDTO) {
        if (this.serversService.deregisterServer(deregisterServerDTO) != null) {
            return ResponseEntity.ok("Deregistered server: " + deregisterServerDTO.getId());
        }
        return ResponseEntity.status(400).body("Could not deregister server: " + deregisterServerDTO.getId());
    }
}
