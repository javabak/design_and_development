package com.ecommerce.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "com.ecommerce", importOptions = {ImportOption.DoNotIncludeTests.class})
public class FFLayeredArchitectureTest {

    @ArchTest
    static final ArchRule analytics_layer_boundaries_rule =
            Architectures.layeredArchitecture()
                    .consideringAllDependencies()
                    .layer("Web").definedBy("com.ecommerce.web..")
                    .layer("Processing").definedBy("com.ecommerce.processing..")
                    .layer("MainDB").definedBy("com.ecommerce.repository.main..")
                    .layer("AnalyticsDB").definedBy("com.ecommerce.repository.analytics..")

                    // К аналитической БД может обращаться ТОЛЬКО слой Processing
                    .whereLayer("AnalyticsDB").mayOnlyBeAccessedByLayers("Processing")

                    // К основной БД (MainDB) разрешено обращаться ТОЛЬКО слою Web
                    // (тем самым слою Processing запрещен доступ к MainDB)
                    .whereLayer("MainDB").mayOnlyBeAccessedByLayers("Web");
}


