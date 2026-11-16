package com.smartresume.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {
    public record Template(String code, String name, String description, String style) {}

    @GetMapping
    public List<Template> all() {
        return List.of(
            new Template("CLASSIC", "Classic", "ATS-friendly, clean single-column layout", "classic"),
            new Template("MODERN_SIDEBAR", "Modern Sidebar", "Strong left sidebar with contact and skills", "modern-sidebar"),
            new Template("CREATIVE_PORTFOLIO", "Creative Portfolio", "Expressive portfolio-style header and accents", "creative-portfolio"),
            new Template("ATS_FRIENDLY", "ATS Friendly", "Plain, recruiter-friendly single column", "ats-friendly"),
            new Template("DEVELOPER_TECH", "Developer / Tech", "Monospace details and technical focus", "developer-tech"),
            new Template("DARK_MODE", "Dark Mode", "Dark charcoal visual style for web preview", "dark-mode"),
            new Template("TIMELINE", "Timeline", "Chronological experience and education styling", "timeline"),
            new Template("ELEGANT_SERIF", "Elegant Serif", "Editorial serif typography and fine rules", "elegant-serif"),
            new Template("CORPORATE", "Corporate Professional", "Formal navy corporate two-column layout", "corporate"),
            new Template("STARTUP", "Startup / Founder", "Bold headline and high-contrast accent", "startup"),
            new Template("ACADEMIC", "Academic CV", "Research, education and certification focused", "academic"),
            new Template("STUDENT", "Student / Entry-Level", "Education and projects focused for freshers", "student"),
            new Template("EXECUTIVE", "Executive", "Restrained executive typography and spacing", "executive"),
            new Template("INFOGRAPHIC", "Infographic", "Visual skills and section markers", "infographic"),
            new Template("GRADIENT", "Gradient / Colorful", "Colorful gradient header treatment", "gradient"),
            new Template("TWO_COLUMN", "Two-Column Compact", "Compact sidebar and content column", "two-column")
        );
    }
}
