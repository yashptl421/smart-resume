package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity @Table(name="skills")
public class Skill {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="resume_id",nullable=false) private Resume resume;
 @Column(nullable=false,length=100) private String skillName;
 @Column(length=50) private String skillLevel;
 public Long getId(){return id;} @JsonIgnore public Resume getResume(){return resume;} public void setResume(Resume v){resume=v;}
 public String getSkillName(){return skillName;} public void setSkillName(String v){skillName=v;}
 public String getSkillLevel(){return skillLevel;} public void setSkillLevel(String v){skillLevel=v;}
}
