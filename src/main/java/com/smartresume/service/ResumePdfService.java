
package com.smartresume.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.smartresume.entity.*;
import com.smartresume.repository.ResumeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Set;

@Service
public class ResumePdfService {

    private final ResumeRepository resumes;

    public ResumePdfService(ResumeRepository resumes) {
        this.resumes = resumes;
    }

    @Transactional(readOnly = true)
    public byte[] createPdf(Long resumeId, Long userId) {

        Resume resume = resumes.findByIdAndUser_Id(resumeId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Resume not found"));

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            String html = renderHtml(resume);

            // Ensure generated HTML is XML-compatible.
            html = sanitizeHtmlForPdf(html);

            builder.withHtmlContent(html, null);
            builder.toStream(output);
            builder.run();

            byte[] pdfBytes = output.toByteArray();

            if (pdfBytes.length == 0) {
                throw new IllegalStateException("Generated PDF is empty");
            }

            return pdfBytes;

        } catch (Exception ex) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to generate PDF for this resume: " + ex.getMessage(),
                    ex
            );
        }
    }

    /**
     * Converts common HTML named entities to XML-compatible
     * numeric character references.
     */
    private String sanitizeHtmlForPdf(String html) {

        if (html == null) {
            return "";
        }

        return html
                .replace("&nbsp;", "&#160;")
                .replace("&mdash;", "&#8212;")
                .replace("&ndash;", "&#8211;")
                .replace("&rsquo;", "&#8217;")
                .replace("&lsquo;", "&#8216;")
                .replace("&rdquo;", "&#8221;")
                .replace("&ldquo;", "&#8220;")
                .replace("&bull;", "&#8226;")
                .replace("&copy;", "&#169;");
    }

    private String renderHtml(Resume r) {

        String template = safe(r.getTemplateName())
                .toLowerCase(Locale.ROOT)
                .replace('_', '-');

        StringBuilder html = new StringBuilder(
                "<!DOCTYPE html><html><head>" +
                        "<meta charset='UTF-8'/>" +
                        "<style>"
        )
                .append("@page{size:A4;margin:18mm 16mm}")
                .append("*{box-sizing:border-box}")
                .append("body{font-family:Arial,Helvetica,sans-serif;" +
                        "color:#202938;font-size:10pt;line-height:1.45}")
                .append(".sheet{padding:0}")
                .append(".header{padding-bottom:12px;border-bottom:2px solid #3159d9;" +
                        "margin-bottom:14px}")
                .append("h1{font-size:25pt;margin:0 0 5px;color:#172033}")
                .append(".contact{font-size:9pt;color:#667085}")
                .append("h2{font-size:11pt;text-transform:uppercase;" +
                        "letter-spacing:1px;color:#3159d9;" +
                        "border-bottom:1px solid #dfe4ee;" +
                        "padding-bottom:4px;margin:17px 0 8px}")
                .append("p{margin:4px 0}")
                .append(".item{margin:0 0 9px;page-break-inside:avoid}")
                .append(".item-title{font-weight:bold}")
                .append(".muted{color:#667085;font-size:9pt}")
                .append("ul{margin:4px 0;padding-left:18px}")
                .append("li{margin:2px 0}")
                .append(".columns{width:100%;border-collapse:collapse}")
                .append(".columns td{vertical-align:top}")
                .append(".sidebar{width:31%;background:#f1f4fb;padding:12px}")
                .append(".main{padding:0 0 0 15px}")

                // Template styles
                .append(".template-modern .header{border-color:#0f766e}")
                .append(".template-professional .header{border-color:#243b63}")
                .append(".template-minimal .header{border:0}")
                .append(".template-creative .header{" +
                        "border-left:5px solid #a855f7;padding-left:12px}")
                .append(".template-executive{font-family:Georgia,serif}")
                .append(".template-executive .header{border-color:#9a743c}")
                .append(".template-student .header{border-color:#2563eb}")
                .append(".template-two-column .header{border-color:#3159d9}")
                .append(".template-modern-sidebar .header," +
                        ".template-corporate .header{border-color:#1e3a8a}")
                .append(".template-creative-portfolio .header{" +
                        "border-color:#8b5cf6;border-bottom-width:5px}")
                .append(".template-ats-friendly{font-family:Arial,sans-serif}")
                .append(".template-developer-tech{font-family:Courier,monospace}")
                .append(".template-dark-mode{" +
                        "background:#172033;color:#f8fafc;padding:12px}")
                .append(".template-dark-mode h2{color:#93c5fd}")
                .append(".template-timeline h2{" +
                        "border-left:4px solid #2563eb;padding-left:8px}")
                .append(".template-elegant-serif,.template-academic," +
                        ".template-executive{font-family:Georgia,serif}")
                .append(".template-elegant-serif h2{color:#8b7355}")
                .append(".template-startup .header{" +
                        "border-color:#f97316;border-bottom-width:5px}")
                .append(".template-infographic h2{" +
                        "color:#0f766e;background:#ecfeff;padding:5px}")
                .append(".template-gradient .header{" +
                        "border-color:#a855f7;border-bottom-width:5px}")

                .append("</style></head><body>")
                .append("<div class='sheet template-")
                .append(escape(template))
                .append("'>")

                // Resume header
                .append("<div class='header'>")
                .append("<h1>")
                .append(escape(r.getFullName()))
                .append("</h1>")
                .append("<div class='contact'>")
                .append(joinContact(
                        r.getEmail(),
                        r.getPhone(),
                        r.getAddress()
                ))
                .append("</div></div>");

        boolean twoColumn = Set.of(
                "two-column",
                "modern-sidebar",
                "corporate"
        ).contains(template);

        if (twoColumn) {
            html.append(
                    "<table class='columns'><tr>" +
                            "<td class='sidebar'></td>" +
                            "<td class='main'>"
            );
        }

        // Professional Summary
        if (present(r.getProfessionalSummary())) {

            html.append("<h2>Profile</h2><p>")
                    .append(escape(r.getProfessionalSummary()))
                    .append("</p>");
        }

        // Education
        if (!r.getEducation().isEmpty()) {

            html.append("<h2>Education</h2>");

            for (Education e : r.getEducation()) {

                html.append("<div class='item'>")
                        .append("<div class='item-title'>")
                        .append(escape(e.getDegree()));

                if (present(e.getFieldOfStudy())) {
                    html.append(" · ")
                            .append(escape(e.getFieldOfStudy()));
                }

                html.append("</div>")
                        .append("<div>")
                        .append(escape(e.getInstitution()))
                        .append("</div>")
                        .append("<div class='muted'>")
                        .append(escape(e.getStartMonth()))
                        .append(" ")
                        .append(value(e.getStartYear()))
                        .append(" – ")
                        .append(escape(e.getEndMonth()))
                        .append(" ")
                        .append(value(e.getEndYear()));

                if (present(e.getGrade())) {
                    html.append(" · ")
                            .append(escape(e.getGrade()));
                }

                html.append("</div></div>");
            }
        }

        // Skills - immediately after Education
        appendSkills(html, r);

        // Experience
        if (!r.getExperience().isEmpty()) {

            html.append("<h2>Experience</h2>");

            for (Experience e : r.getExperience()) {

                html.append("<div class='item'>")
                        .append("<div class='item-title'>")
                        .append(escape(e.getJobTitle()))
                        .append(" · ")
                        .append(escape(e.getCompanyName()))
                        .append("</div>")
                        .append("<div class='muted'>")
                        .append(value(e.getStartDate()))
                        .append(" – ")
                        .append(e.getEndDate() == null
                                ? "Present"
                                : value(e.getEndDate()))
                        .append("</div><p>")
                        .append(escape(e.getDescription()))
                        .append("</p></div>");
            }
        }

        // Projects
        if (!r.getProjects().isEmpty()) {

            html.append("<h2>Projects</h2>");

            for (Project x : r.getProjects()) {

                html.append("<div class='item'>")
                        .append("<div class='item-title'>")
                        .append(escape(x.getProjectName()))
                        .append("</div>")
                        .append("<div class='muted'>")
                        .append(escape(x.getTechnologies()))
                        .append("</div><p>")
                        .append(escape(x.getDescription()))
                        .append("</p>");

                if (present(x.getProjectUrl())) {
                    html.append("<p class='muted'>")
                            .append(escape(x.getProjectUrl()))
                            .append("</p>");
                }

                html.append("</div>");
            }
        }

        // Certifications
        if (!r.getCertifications().isEmpty()) {

            html.append("<h2>Certifications</h2>");

            for (Certification c : r.getCertifications()) {

                html.append("<div class='item'>")
                        .append("<span class='item-title'>")
                        .append(escape(c.getCertificateName()))
                        .append("</span> · ")
                        .append(escape(c.getIssuingOrganization()))
                        .append("</div>");
            }
        }

        if (twoColumn) {
            html.append("</td></tr></table>");
        }

        return html.append("</div></body></html>").toString();
    }

    /**
     * Appends skills section.
     */
    private void appendSkills(StringBuilder h, Resume r) {

        if (r.getSkills() == null || r.getSkills().isEmpty()) {
            return;
        }

        h.append("<h2>Skills</h2><ul>");

        for (Skill s : r.getSkills()) {

            if (s != null && present(s.getSkillName())) {
                h.append("<li>")
                        .append(escape(s.getSkillName()))
                        .append("</li>");
            }
        }

        h.append("</ul>");
    }

    /**
     * Builds contact details.
     *
     * IMPORTANT:
     * Uses XML-compatible numeric character reference instead
     * of the undeclared HTML entity &nbsp;.
     */
    private String joinContact(String... values) {

        StringBuilder b = new StringBuilder();

        for (String v : values) {

            if (present(v)) {

                if (b.length() > 0) {
                    // XML-compatible non-breaking space: &#160;
                    b.append(" &#160;|&#160; ");
                }

                b.append(escape(v));
            }
        }

        return b.toString();
    }

    private boolean present(String s) {
        return s != null && !s.isBlank();
    }

    private String value(Object v) {
        return v == null ? "" : v.toString();
    }

    private String safe(String s) {
        return s == null ? "classic" : s;
    }

    /**
     * Escapes dynamic resume content before inserting it into HTML.
     */
    private String escape(Object v) {

        if (v == null) {
            return "";
        }

        return v.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
