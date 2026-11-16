package com.smartresume.service;
import com.smartresume.dto.ResumeRequest;
import com.smartresume.entity.*;
import com.smartresume.repository.ResumeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service @Transactional
public class ResumeService {
 private final ResumeRepository repository;
 public ResumeService(ResumeRepository repository){this.repository=repository;}

 public Resume create(ResumeRequest r, AppUser user){Resume e=new Resume(); e.setUser(user); apply(e,r); return repository.save(e);}
 @Transactional(readOnly=true) public List<Resume> findAll(Long userId){return repository.findAllByUser_IdOrderByUpdatedAtDesc(userId);}
 @Transactional(readOnly=true) public Resume findById(Long id, Long userId){return repository.findByIdAndUser_Id(id,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resume not found"));}
 public Resume update(Long id,ResumeRequest r,Long userId){Resume e=findById(id,userId); apply(e,r); return repository.save(e);}
 public void delete(Long id,Long userId){repository.delete(findById(id,userId));}

 private void apply(Resume e,ResumeRequest r){
  e.setFullName(r.fullName());e.setEmail(r.email());e.setPhone(r.phone());e.setAddress(r.address());
  e.setProfessionalSummary(r.professionalSummary());if(r.templateName()!=null&&!r.templateName().isBlank())e.setTemplateName(r.templateName());
  e.clearChildren();
  if(r.education()!=null) for(var x:r.education()){Education c=new Education();c.setResume(e);c.setInstitution(x.institution());c.setDegree(x.degree());c.setFieldOfStudy(x.fieldOfStudy());c.setStartMonth(x.startMonth());c.setStartYear(x.startYear());c.setEndMonth(x.endMonth());c.setEndYear(x.endYear());c.setGrade(x.grade());e.getEducation().add(c);}
  if(r.experience()!=null) for(var x:r.experience()){Experience c=new Experience();c.setResume(e);c.setCompanyName(x.companyName());c.setJobTitle(x.jobTitle());c.setStartDate(x.startDate());c.setEndDate(x.endDate());c.setDescription(x.description());e.getExperience().add(c);}
  if(r.skills()!=null) for(var x:r.skills()){Skill c=new Skill();c.setResume(e);c.setSkillName(x.skillName());c.setSkillLevel(x.skillLevel());e.getSkills().add(c);}
  if(r.projects()!=null) for(var x:r.projects()){Project c=new Project();c.setResume(e);c.setProjectName(x.projectName());c.setDescription(x.description());c.setTechnologies(x.technologies());c.setProjectUrl(x.projectUrl());e.getProjects().add(c);}
  if(r.certifications()!=null) for(var x:r.certifications()){Certification c=new Certification();c.setResume(e);c.setCertificateName(x.certificateName());c.setIssuingOrganization(x.issuingOrganization());c.setIssueDate(x.issueDate());c.setCredentialUrl(x.credentialUrl());e.getCertifications().add(c);}
 }
}
