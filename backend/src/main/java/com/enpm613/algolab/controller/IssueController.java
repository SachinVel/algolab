package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.entity.Issue;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.service.IssueService;
import com.enpm613.algolab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/issues")
@AllArgsConstructor
@CrossOrigin("*")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/viewAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public List<Issue> getAllIssues(@AuthenticationPrincipal UserDetails user) {
        List<Issue> feedbackList = issueService.getIssues();
        return feedbackList;
    }

    @PostMapping("/report")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','INSTRUCTOR')")
    public ResponseEntity<Object> createIssue(@AuthenticationPrincipal UserDetails user, @RequestBody Issue issue) {
        String curUsername = user.getUsername();
        User curUser = userService.getUserByUsername(curUsername);
        Issue savedIssue = issueService.createIssue(issue, curUser);
        return ResponseEntity.ok("true");
    }


    @GetMapping("/getIssue/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public Issue getIssue(@AuthenticationPrincipal UserDetails user, @PathVariable String id) {
        return issueService.getIssueById(id);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public ResponseEntity<Object> deleteIssue(@AuthenticationPrincipal UserDetails user, @PathVariable String id) {
        issueService.deleteIssue(id);
        return ResponseEntity.ok("true");
    }

}
