package com.jyujyu.dayonetest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

public class ArchitectureTest {

  JavaClasses javaClasses;

  @BeforeEach
  public void beforeEach() {
    javaClasses =
        new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests()) // 테스트 클래스는 이 검증에서 빠진다는 의미입니다.
            .importPackages("com.jyujyu.dayonetest");
  }

  @Test
  @DisplayName("controller 패키지 안에 있는 클래스들은 Controller로 끝나야합니다.")
  public void controllerTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..controller") // controller 안에 있는 패키지들은 controller 로 끝나야한다
            .should()
            .haveSimpleNameEndingWith("Api");
    ArchRule annotationRule =
        classes()
            .that()
            .resideInAnyPackage("..controller")
            .should()
            .beAnnotatedWith(RestController.class)
            .orShould()
            .beAnnotatedWith(Controller.class);
    rule.check(javaClasses);
    annotationRule.check(javaClasses);
  }

  @Test
  @DisplayName("controller 패키지 안에 있는 클래스는 Request로 끝나야합니다.")
  public void requestTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..request..") // ..의미는 이전에 모든 패키지는 무시하고 저런 이름이 있어야한다.
            .should()
            .haveSimpleNameEndingWith("Request");

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("controller 패키지 안에 있는 클래스는 Response로 끝나야합니다.")
  public void responseTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..response..") // ..의미는 이전에 모든 패키지는 무시하고 저런 이름이 있어야한다.
            .should()
            .haveSimpleNameEndingWith("Response");

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("repository 패키지 안에 있는 클래스는 Repository로 끝나야 하고, 인터페이스여야 합니다.")
  public void repositoryTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..repository..")
            .should()
            .haveSimpleNameEndingWith("Repository")
            .andShould()
            .beInterfaces();

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("service 패키지 안에 있는 클래스는 Service로 끝나야 하고, @Service 어노테이션이 붙어있어야 합니다.")
  public void serviceTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..service..")
            .should()
            .haveSimpleNameEndingWith("Service")
            .andShould()
            .beAnnotatedWith(Service.class);

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("config 패키지 안에 있는 클래스는 Config로 끝나야하고 @Configuration 어노테이션이 붙어있어야 합니다.")
  public void configTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..config..")
            .should()
            .haveSimpleNameEndingWith("Config")
            .andShould()
            .beAnnotatedWith(Configuration.class);

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Controller는 Service와 Request/Response를 사용할 수 있음")
  public void controllerDependencyTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..controller")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..request..", "..response..", "..service..");

    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Controller는 의존되지 않음")
  public void controllerDependencyTest2() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..controller")
            .should()
            .onlyHaveDependentClassesThat()
            .resideInAnyPackage("..controller");
    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Controller는 모델을 사용할 수 없음")
  public void controllerDependencyTest3() {
    ArchRule rule =
        noClasses()
            .that()
            .resideInAnyPackage("..controller")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..model..");
    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Service는 Controller를 의존하면 안됨")
  public void serviceDependencyTest() {
    ArchRule rule =
        noClasses()
            .that()
            .resideInAnyPackage("..service..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..controller");
    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Model은 오직 Service와 Repository에 의해 의존됨")
  public void modelDependencyTest() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..model..")
            .should()
            .onlyHaveDependentClassesThat()
            .resideInAnyPackage("..repository..", "..service..", "..model..");
    rule.check(javaClasses);
  }

  @Test
  @DisplayName("Model은 아무것도 의존하지 않는다")
  public void modelDependencyTest2() {
    ArchRule rule =
        classes()
            .that()
            .resideInAnyPackage("..model..")
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage("..model..", "java..", "jakarta..");
    rule.check(javaClasses);
  }
}
