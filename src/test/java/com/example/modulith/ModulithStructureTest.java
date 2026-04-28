package com.example.modulith;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ModulithStructureTest {

    @Test
    void verifiesModuleBoundaries() {
        ApplicationModules.of(ModulithDemoApplication.class).verify();
    }

    @Test
    void keepsEntitiesIndependentOfOuterRings() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.modulith");

        noClasses()
                .that().resideInAPackage("..entity..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..usecase..",
                        "..interfaceadapter..",
                        "..framework..",
                        "org.springframework..",
                        "jakarta.."
                )
                .check(classes);
    }

    @Test
    void keepsUseCasesIndependentOfFrameworksAndInterfaceAdapters() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.modulith");

        noClasses()
                .that().resideInAPackage("..usecase..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..interfaceadapter..",
                        "..framework..",
                        "org.springframework..",
                        "jakarta.."
                )
                .check(classes);
    }
}
