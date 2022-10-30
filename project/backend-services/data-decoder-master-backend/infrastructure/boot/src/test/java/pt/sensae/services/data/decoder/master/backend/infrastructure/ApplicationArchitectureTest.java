package pt.sensae.services.data.decoder.master.backend.infrastructure;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "pt.sensae.services")
public class ApplicationArchitectureTest {

    @ArchTest
    static final ArchRule architecture = Architectures.onionArchitecture()
            .domainModels("..domain..")
            .domainServices("..domainservices..")
            .applicationServices("..application..")
            .adapter("amqp connector", "..amqp..")
            .adapter("in memory persistence", "..memory..")
            .adapter("postgres persistence", "..postgres..")
            .adapter("graphql endpoint", "..graphql..")
            .ignoreDependency(JavaClass.Predicates.resideInAPackage("..boot.."), DescribedPredicate.alwaysTrue());

    @ArchTest
    static final ArchRule domainMustNotDependOnFrameworks = ArchRuleDefinition.noClasses()
       .that().resideInAnyPackage("..domain..")
       .should().dependOnClassesThat().haveNameMatching("org.springframework.")
       .orShould().dependOnClassesThat().haveNameMatching("javax.persistence.*")
       .because("Domain should be free from Frameworks");
}
