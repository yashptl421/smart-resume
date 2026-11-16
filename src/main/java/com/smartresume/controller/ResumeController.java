package com.smartresume.controller;
import com.smartresume.dto.ResumeRequest;
import com.smartresume.entity.AppUser;
import com.smartresume.entity.Resume;
import com.smartresume.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.smartresume.service.ResumePdfService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/resumes")
public class ResumeController {
 private final ResumeService service;
 private final ResumePdfService pdfService;
 public ResumeController(ResumeService service, ResumePdfService pdfService){this.service=service;this.pdfService=pdfService;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public Resume create(@Valid @RequestBody ResumeRequest r,@AuthenticationPrincipal AppUser user){return service.create(r,user);}
 @GetMapping public List<Resume> all(@AuthenticationPrincipal AppUser user){return service.findAll(user.getId());}
 @GetMapping("/{id}") public Resume one(@PathVariable Long id,@AuthenticationPrincipal AppUser user){return service.findById(id,user.getId());}
 @PutMapping("/{id}") public Resume update(@PathVariable Long id,@Valid @RequestBody ResumeRequest r,@AuthenticationPrincipal AppUser user){return service.update(id,r,user.getId());}
 @GetMapping(value="/{id}/pdf", produces="application/pdf")
 public ResponseEntity<byte[]> pdf(@PathVariable Long id,@AuthenticationPrincipal AppUser user){
  byte[] bytes=pdfService.createPdf(id,user.getId());
  return ResponseEntity.ok()
    .contentType(MediaType.APPLICATION_PDF)
    .contentLength(bytes.length)
    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"resume-"+id+".pdf\"")
    .header(HttpHeaders.CACHE_CONTROL,"no-store, no-cache, must-revalidate")
    .body(bytes);
 }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id,@AuthenticationPrincipal AppUser user){service.delete(id,user.getId());}
}
