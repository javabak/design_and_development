package com.ecommerce.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class FFCycleDependencyTest {

    @Test
    void ensureNoCyclicDependenciesAcrossModules() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ecommerce");

        SlicesRuleDefinition.slices()
                .matching("com.ecommerce.(*)..")
                .should().beFreeOfCycles()
                .check(importedClasses);
    }
}


