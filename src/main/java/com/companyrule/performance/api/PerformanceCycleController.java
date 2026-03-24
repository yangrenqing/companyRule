package com.companyrule.performance.api;

import com.companyrule.performance.application.PerformanceCycleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/performance/cycles")
public class PerformanceCycleController {

    private final PerformanceCycleService performanceCycleService;

    public PerformanceCycleController(PerformanceCycleService performanceCycleService) {
        this.performanceCycleService = performanceCycleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCycleResponse createCycle(@Valid @RequestBody CreatePerformanceCycleRequest request) {
        return PerformanceCycleResponse.from(performanceCycleService.createCycle(request));
    }

    @GetMapping("/{id}")
    public PerformanceCycleResponse getCycle(@PathVariable("id") String id) {
        return PerformanceCycleResponse.from(performanceCycleService.getCycle(id));
    }

    @PostMapping("/{id}/plans")
    @ResponseStatus(HttpStatus.CREATED)
    public PerformancePlanResponse generatePlan(@PathVariable("id") String id) {
        return PerformancePlanResponse.from(performanceCycleService.generatePlan(id));
    }
}
