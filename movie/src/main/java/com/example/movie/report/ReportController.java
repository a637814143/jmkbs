package com.example.movie.report;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.report.dto.CreateReportRequest;
import com.example.movie.report.dto.ReportView;
import com.example.movie.report.dto.ResolveReportRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ReportView create(@Valid @RequestBody CreateReportRequest request) {
        return reportService.create(request);
    }

    @GetMapping("/pending")
    public List<ReportView> pending() {
        return reportService.pending();
    }

    @PostMapping("/{reportId}/resolve")
    public ReportView resolve(@PathVariable Long reportId, @Valid @RequestBody ResolveReportRequest request) {
        return reportService.resolve(reportId, request);
    }

    @GetMapping
    public List<ReportView> listAll() {
        return reportService.listAll();
    }
}
