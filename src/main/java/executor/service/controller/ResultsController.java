package executor.service.controller;

import executor.service.model.response.ScenarioResultResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultsController {


    @GetMapping
    public ResponseEntity<ScenarioResultResponse> getResultByScenario(@RequestParam Integer scenarioId,
                                                                      @RequestParam Integer userId) {
        return ResponseEntity.ok(getEmptyResult());
    }

    private ScenarioResultResponse getEmptyResult() {
        return new ScenarioResultResponse(null,null,null,null, null);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<ScenarioResultResponse>> getResults() {
        return ResponseEntity.ok(List.of(getEmptyResult(),getEmptyResult()));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatusCode> deleteScenario(@RequestParam Integer scenarioId,
                                                         @RequestParam Integer userId) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(202));
    }

}
