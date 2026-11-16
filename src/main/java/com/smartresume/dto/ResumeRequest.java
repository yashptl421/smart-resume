package com.smartresume.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record ResumeRequest(
 @NotBlank @Size(max=120) String fullName,
 @NotBlank @Email @Size(max=150) String email,
 @Pattern(regexp="^(\\+?[0-9]{10,13})?$", message="Enter a valid phone number with 10–13 digits.") @Size(max=25) String phone,
 @Size(max=500) String address,
 @Size(max=1000) String professionalSummary,
 String templateName,
 List<@Valid EducationRequest> education,
 List<@Valid ExperienceRequest> experience,
 List<@Valid SkillRequest> skills,
 List<@Valid ProjectRequest> projects,
 List<@Valid CertificationRequest> certifications
) {
 public record EducationRequest(@NotBlank String institution,@NotBlank String degree,String fieldOfStudy,String startMonth,Integer startYear,String endMonth,Integer endYear,String grade){}
 public record ExperienceRequest(@NotBlank String companyName,@NotBlank String jobTitle,LocalDate startDate,LocalDate endDate,String description){}
 public record SkillRequest(@NotBlank String skillName,String skillLevel){}
 public record ProjectRequest(@NotBlank String projectName,String description,String technologies,String projectUrl){}
 public record CertificationRequest(@NotBlank String certificateName,String issuingOrganization,LocalDate issueDate,String credentialUrl){}
}
