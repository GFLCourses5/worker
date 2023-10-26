package executor.service.controller;

import executor.service.model.response.ScenarioResultResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static executor.service.Routes.RESULTS;

@RestController
@RequestMapping(RESULTS)
public class ResultsController {


    @GetMapping
    public ResponseEntity<ScenarioResultResponse> getResultByScenario(@RequestParam(name = "scenarioId")  Integer scenarioId) {
        return ResponseEntity.ok(getEmptyResult());
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResultResponse>> getUserResults(@RequestParam(name = "userId") Integer userId) {
        return ResponseEntity.ok(List.of(getEmptyResult(), getEmptyResult()));
    }

    private ScenarioResultResponse getEmptyResult() {
        return new ScenarioResultResponse(null,null,null,null, null);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatusCode> deleteResult(@RequestParam(name="scenarioId") Integer scenarioId,
                                                         @RequestParam(name="resultId") Integer userId) {
        return new ResponseEntity<>(HttpStatusCode.valueOf(202));
    }

}
