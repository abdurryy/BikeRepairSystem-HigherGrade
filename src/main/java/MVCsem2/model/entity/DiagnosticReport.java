package MVCsem2.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the diagnostic report associated with a repair order.
 */
public class DiagnosticReport {
    private List<String> diagnosticResults;

    /**
     * Initializes an empty diagnostic report.
     */
    public DiagnosticReport() {
        this.diagnosticResults = new ArrayList<>();
    }

    /**
     * Constructs a diagnostic report by duplicating another report.
     */
    public DiagnosticReport(DiagnosticReport original) {
        this();

        if (original != null) {
            this.diagnosticResults.addAll(original.getDiagnosticResults());
        }
    }

    /**
     * Appends a diagnostic result to the report.
     */
    public void addDiagnosticResult(String diagTaskResult) {
        if (diagTaskResult == null || diagTaskResult.trim().isEmpty()) {
            return;
        }

        diagnosticResults.add(diagTaskResult);
    }

    /**
     * Retrieves all diagnostic results in the report.
     */
    public List<String> getDiagnosticResults() {
        return new ArrayList<>(diagnosticResults);
    }

    /**
     * Determines if the report contains any diagnostic results.
     */
    public boolean hasResults() {
        return !diagnosticResults.isEmpty();
    }
}