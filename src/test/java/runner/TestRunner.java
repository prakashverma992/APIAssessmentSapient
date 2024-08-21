package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"stepDefinitions"},
        tags = "@ApiAssessment",
        monochrome = true,
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-html-reports.html",
                "json:target/cucumber-reports/cucumber-html-reports-json/common.json"}
)
public class TestRunner {

}
