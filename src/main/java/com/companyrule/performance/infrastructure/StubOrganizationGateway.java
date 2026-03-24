package com.companyrule.performance.infrastructure;

import com.companyrule.performance.application.OrganizationGateway;
import org.springframework.stereotype.Component;

@Component
public class StubOrganizationGateway implements OrganizationGateway {

    @Override
    public boolean organizationExists(String organizationId) {
        return organizationId != null && !organizationId.isBlank();
    }

    @Override
    public int estimateTargetEmployeeCount(String organizationId) {
        return 1;
    }
}
