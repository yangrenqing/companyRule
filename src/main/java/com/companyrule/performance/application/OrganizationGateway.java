package com.companyrule.performance.application;

public interface OrganizationGateway {

    boolean organizationExists(String organizationId);

    int estimateTargetEmployeeCount(String organizationId);
}
