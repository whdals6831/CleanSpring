package com.jjm.cleanspring.unit.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.jjm.cleanspring", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerDependencyTest {
    private final String PACKAGE_NAME = "com.jjm.cleanspring";
    private final String INFRASTRUCTURE_LAYER = "Infrastructure";
    private final String ADAPTER_LAYER = "Adapter";
    private final String APPLICATION_LAYER = "Application";
    private final String DOMAIN_LAYER = "Domain";
    private final String INFRASTRUCTURE_PACKAGE = PACKAGE_NAME + ".infrastructure..";
    private final String ADAPTER_PACKAGE = PACKAGE_NAME + ".adapter..";
    private final String APPLICATION_PACKAGE = PACKAGE_NAME + ".application..";
    private final String DOMAIN_PACKAGE = PACKAGE_NAME + ".domain..";
    private final String GLOBAL_PACKAGE = PACKAGE_NAME + ".(*)..";

    /**
     * 레이어검사 : 모든 계층은 올바른 의존성을 가져야한다.
     * 화살표는 의존하는 방향이다.
     * Domain <- Application <- Adapter <- Infrastructure
     */
    @ArchTest()
    public LayeredArchitecture layer_dependencies_are_respected = layeredArchitecture()
            .consideringAllDependencies()
            .layer(INFRASTRUCTURE_LAYER)
            .definedBy(INFRASTRUCTURE_PACKAGE)
            .layer(ADAPTER_LAYER)
            .definedBy(ADAPTER_PACKAGE)
            .layer(APPLICATION_LAYER)
            .definedBy(APPLICATION_PACKAGE)
            .layer(DOMAIN_LAYER)
            .definedBy(DOMAIN_PACKAGE)
            .whereLayer(INFRASTRUCTURE_LAYER)
            .mayNotBeAccessedByAnyLayer()
            .whereLayer(ADAPTER_LAYER)
            .mayOnlyBeAccessedByLayers(INFRASTRUCTURE_LAYER)
            .whereLayer(APPLICATION_LAYER)
            .mayOnlyBeAccessedByLayers(ADAPTER_LAYER, INFRASTRUCTURE_LAYER)
            .whereLayer(DOMAIN_LAYER)
            .mayOnlyBeAccessedByLayers(APPLICATION_LAYER, ADAPTER_LAYER, INFRASTRUCTURE_LAYER);

    /**
     * 필드주입검사 : 모든 클래스는 필드주입을 사용하지 않는다.
     */
    @ArchTest
    public ArchRule not_use_field_injection = fields().that()
                                                      .areDeclaredInClassesThat()
                                                      .resideInAPackage(GLOBAL_PACKAGE)
                                                      .should()
                                                      .notBeAnnotatedWith(Autowired.class);

    /**
     * 순환참조검사 : 모든 클래스는 순환 의존성을 가지면 안 된다.
     */
    @ArchTest
    public ArchRule no_cycle_dependencies = slices().matching(GLOBAL_PACKAGE)
                                                    .should()
                                                    .beFreeOfCycles();
}