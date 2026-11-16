package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="resumes")
public class Resume {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id", nullable=false) @JsonIgnore private AppUser user;
 @Column(nullable=false,length=120) private String fullName;
 @Column(nullable=false,length=150) private String email;
 @Column(length=25) private String phone;
 @Column(length=500) private String address;
 @Column(length=1000) private String professionalSummary;
 @Column(length=100) private String templateName="CLASSIC";
 private LocalDateTime createdAt;
 private LocalDateTime updatedAt;
 @OneToMany(mappedBy="resume",cascade=CascadeType.ALL,orphanRemoval=true) private List<Education> education=new ArrayList<>();
 @OneToMany(mappedBy="resume",cascade=CascadeType.ALL,orphanRemoval=true) private List<Experience> experience=new ArrayList<>();
 @OneToMany(mappedBy="resume",cascade=CascadeType.ALL,orphanRemoval=true) private List<Skill> skills=new ArrayList<>();
 @OneToMany(mappedBy="resume",cascade=CascadeType.ALL,orphanRemoval=true) private List<Project> projects=new ArrayList<>();
 @OneToMany(mappedBy="resume",cascade=CascadeType.ALL,orphanRemoval=true) private List<Certification> certifications=new ArrayList<>();
 @PrePersist void create(){createdAt=LocalDateTime.now();updatedAt=createdAt;}
 @PreUpdate void update(){updatedAt=LocalDateTime.now();}
 public void clearChildren(){education.clear();experience.clear();skills.clear();projects.clear();certifications.clear();}
 public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;}
 public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getAddress(){return address;} public void setAddress(String v){address=v;} public String getProfessionalSummary(){return professionalSummary;} public void setProfessionalSummary(String v){professionalSummary=v;}
 public String getTemplateName(){return templateName;} public void setTemplateName(String v){templateName=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
 public List<Education> getEducation(){return education;} public List<Experience> getExperience(){return experience;} public List<Skill> getSkills(){return skills;}
 public List<Project> getProjects(){return projects;} public List<Certification> getCertifications(){return certifications;}
}
