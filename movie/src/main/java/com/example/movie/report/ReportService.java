package com.example.movie.report;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.domain.report.Report;
import com.example.movie.domain.report.ReportRepository;
import com.example.movie.domain.report.ReportStatus;
import com.example.movie.domain.report.ReportTargetType;
import com.example.movie.domain.user.User;
import com.example.movie.domain.user.UserRepository;
import com.example.movie.report.dto.CreateReportRequest;
import com.example.movie.report.dto.ReportView;
import com.example.movie.report.dto.ResolveReportRequest;

@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public ReportView create(CreateReportRequest request) {
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new IllegalArgumentException("举报用户不存在"));
        Report report = new Report();
        report.setReporter(reporter);
        report.setTargetType(request.getTargetType());
        report.setTargetId(request.getTargetId());
        report.setReason(request.getReason());
        report.setStatus(ReportStatus.PENDING);
        Report saved = reportRepository.save(report);
        return toView(saved);
    }

    @Transactional(readOnly = true)
    public List<ReportView> pending() {
        return reportRepository.findByStatusOrderByCreatedAtAsc(ReportStatus.PENDING).stream().map(this::toView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportView> findByReporter(Long reporterId) {
        return reportRepository.findByReporterIdOrderByCreatedAtDesc(reporterId).stream().map(this::toView)
                .collect(Collectors.toList());
    }

    public ReportView resolve(Long reportId, ResolveReportRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("举报不存在"));
        User admin = userRepository.findById(request.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("处理人不存在"));
        report.setStatus(request.getStatus());
        report.setHandledBy(admin);
        report.setHandledAt(Instant.now());
        if (request.getStatus() == ReportStatus.RESOLVED && report.getTargetType() == ReportTargetType.USER) {
            userRepository.findById(report.getTargetId()).ifPresent(target -> {
                target.setStatus(com.example.movie.domain.user.UserStatus.BANNED);
            });
        }
        return toView(report);
    }

    @Transactional(readOnly = true)
    public List<ReportView> listAll() {
        return reportRepository.findAll().stream().map(this::toView).collect(Collectors.toList());
    }

    private ReportView toView(Report report) {
        return new ReportView(report.getId(), report.getTargetType(), report.getTargetId(), report.getReason(),
                report.getStatus(), report.getCreatedAt(), report.getReporter().getId(),
                report.getReporter().getNickname(), report.getHandledBy() != null ? report.getHandledBy().getId() : null,
                report.getHandledAt());
    }
}
