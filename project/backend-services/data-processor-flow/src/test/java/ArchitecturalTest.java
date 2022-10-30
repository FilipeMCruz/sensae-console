import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "pt.sensae.services")
public class ArchitecturalTest {

    @ArchTest
    static final ArchRule architecture = Architectures.onionArchitecture()
            .domainModels("..domain..")
            .applicationServices("..application..")
            .adapter("amqp internal topic connector", "..internal..")
            .adapter("amqp ingress data topic connector", "..ingress..")
            .adapter("amqp egress data topic connector", "..egress..")
            .adapter("in memory persistence", "..memory..")
            .ignoreDependency(JavaClass.Predicates.resideInAPackage("..boot.."), DescribedPredicate.alwaysTrue())
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule domainMustNotDependOnFrameworks = ArchRuleDefinition.noClasses()
            .that().resideInAnyPackage("..domain..")
            .should().dependOnClassesThat().haveNameMatching("org.eclipse.")
            .orShould().dependOnClassesThat().haveNameMatching("com.fasterxml.")
            .orShould().dependOnClassesThat().haveNameMatching("com.google.")
            .orShould().dependOnClassesThat().haveNameMatching("javax.")
            .because("Domain should be free from Frameworks");
}
