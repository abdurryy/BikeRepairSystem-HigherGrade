package MVCsem2.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiagnosticReportTest {
    private DiagnosticReport reportUnderTest;

    @BeforeEach
    public void setUp() {

        reportUnderTest = new DiagnosticReport();
    }


    @Test
    public void newReportShouldStartWithoutDiagnosticResults() {

        assertFalse(reportUnderTest.hasResults(),
                "A newly created diagnostic report should not contain any results.");
    }

    @Test
    public void wheelDamageResultShouldBeStored() {

        String diagnosticText = "Wheel is damaged";

        reportUnderTest.addDiagnosticResult(diagnosticText);

        assertTrue(reportUnderTest.hasResults(),
                "The report should contain results after a diagnostic result has been added.");
        assertEquals(diagnosticText, reportUnderTest.getDiagnosticResults().get(0),
                "The wheel damage diagnostic result should be stored as the first result.");
    }
    
    @Test
    public void viewScenarioDiagnosticResultsShouldKeepInsertionOrder() {

        String firstResult = "Wheel is damaged";
        String secondResult = "Headlights are broken";

        reportUnderTest.addDiagnosticResult(firstResult);
        reportUnderTest.addDiagnosticResult(secondResult);

        assertEquals(2, reportUnderTest.getDiagnosticResults().size(),
                "The report should contain the two diagnostic results from the scenario.");
        assertEquals(firstResult, reportUnderTest.getDiagnosticResults().get(0),
                "The first diagnostic result should be the wheel damage result.");
        assertEquals(secondResult, reportUnderTest.getDiagnosticResults().get(1),
                "The second diagnostic result should be the headlights result.");
    }

    @Test
    public void copiedReportShouldContainSameScenarioResults() {
        
        String wheelResult = "Wheel is damaged";
        String headlightsResult = "Headlights are broken";

        reportUnderTest.addDiagnosticResult(wheelResult);
        reportUnderTest.addDiagnosticResult(headlightsResult);

        DiagnosticReport copiedReport = new DiagnosticReport(reportUnderTest);

        assertEquals(2, copiedReport.getDiagnosticResults().size(),
                "The copied diagnostic report should contain both scenario results.");
        assertEquals(wheelResult, copiedReport.getDiagnosticResults().get(0),
                "The first copied result should match the original first result.");
        assertEquals(headlightsResult, copiedReport.getDiagnosticResults().get(1),
                "The second copied result should match the original second result.");
    }
}