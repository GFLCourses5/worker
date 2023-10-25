package executor.service.controller;


import executor.service.model.request.Scenario;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static executor.service.Routes.SCENARIOS_v2;

@RestController
@RequestMapping(SCENARIOS_v2)
public class RestScenarioController {

    @GetMapping
    public ResponseEntity<Scenario> getScenario(@RequestParam Integer id) {
        return ResponseEntity.ok(new Scenario());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Scenario>> getScenarios() {
        return ResponseEntity.ok(List.of(new Scenario(), new Scenario()));
    }

    @PostMapping
    public ResponseEntity<Scenario> createScenario(@RequestBody Scenario scenario){
        return ResponseEntity.ok(scenario);
    }

    @PostMapping(value = "/list")
    public ResponseEntity<List<Scenario>> createScenarios(@RequestBody List<Scenario> scenarios) {
        return ResponseEntity.ok(scenarios);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatusCode> deleteScenario(@RequestParam Integer id) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(202));
    }



}
