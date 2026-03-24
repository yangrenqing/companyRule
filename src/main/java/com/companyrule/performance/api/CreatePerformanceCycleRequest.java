package com.companyrule.performance.api;

import jakarta.validation.constraints.NotBlank;

public record CreatePerformanceCycleRequest(
        @NotBlank String name,
        @NotBlank String organizationId
) {
}
