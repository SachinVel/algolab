package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.Issue;
import com.enpm613.algolab.service.IssueService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/view")
    public List<Issue> getIssues() {
        return issueService.getIssues();
    }

    @PostMapping("/report")
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/issues/{id}")
    public Issue getIssue(@PathVariable Long id) {
        return issueService.getIssueById(String.valueOf(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/issues/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(String.valueOf(id));
    }

}
