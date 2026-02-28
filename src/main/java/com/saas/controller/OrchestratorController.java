package com.saas.controller;

import com.saas.services.impl.OrchestratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Orchestrator")
public class OrchestratorController {

    @Autowired
    private OrchestratorServiceImpl orchestratorService;

    @PostMapping
    public ResponseEntity<?> process(@RequestHeader Map<String, String> headers, @RequestBody Object data){
        var requestEvent = orchestratorService.process(headers, data);

        return requestEvent.getStatusMap().stream().noneMatch(status -> status.getStatusCode() == 400) ? new ResponseEntity<>(requestEvent, HttpStatus.OK) :
                new ResponseEntity<>(requestEvent, HttpStatus.BAD_REQUEST);
    }
}
