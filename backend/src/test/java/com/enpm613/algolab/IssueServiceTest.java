package com.enpm613.algolab;

import com.enpm613.algolab.entity.Issue;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.repository.IssueRepository;
import com.enpm613.algolab.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIssues() {
        // Mock data
        List<Issue> mockIssues = new ArrayList<>();
        mockIssues.add(new Issue("1", new User(), Issue.Severity.CRITICAL, "Title 1", "Description 1"));
        mockIssues.add(new Issue("2", new User(), Issue.Severity.MODERATE, "Title 2", "Description 2"));
        System.out.println(new User());

        // Mock repository findAll method
        when(issueRepository.findAll()).thenReturn(mockIssues);

        // Call the service method
        List<Issue> issues = issueService.getIssues();

        // Assertions
        assertNotNull(issues);
        assertEquals(2, issues.size());
        assertEquals("1", issues.get(0).getId());
        assertEquals(Issue.Severity.CRITICAL, issues.get(0).getSeverity());
        assertEquals("Title 1", issues.get(0).getName());
        // Add similar assertions for the second issue
    }

    @Test
    void testCreateIssue() {
        // Mock data
        Issue newIssue = new Issue("1", new User(), Issue.Severity.MINOR, "New Issue", "New Description");

        // Mock repository save method
        when(issueRepository.save(any(Issue.class))).thenReturn(newIssue);

        // Call the service method
        Issue createdIssue = issueService.createIssue(newIssue, new User());

        // Assertions
        assertNotNull(createdIssue);
        assertNotNull(createdIssue.getId());
        assertEquals(newIssue.getSeverity(), createdIssue.getSeverity());
        assertEquals(newIssue.getName(), createdIssue.getName());
        // Add similar assertions for other fields
    }

    @Test
    void testGetIssueById() {
        // Mock data
        String issueId = "1";
        Issue mockIssue = new Issue(issueId, new User(), Issue.Severity.MODERATE, "Test Issue", "Test Description");

        // Mock repository findById method
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(mockIssue));

        // Call the service method
        Issue retrievedIssue = issueService.getIssueById(issueId);

        // Assertions
        assertNotNull(retrievedIssue);
        assertEquals(issueId, retrievedIssue.getId());
        assertEquals(mockIssue.getSeverity(), retrievedIssue.getSeverity());
        assertEquals(mockIssue.getName(), retrievedIssue.getName());
        // Add similar assertions for other fields
    }

    @Test
    void testDeleteIssue() {
        // Mock data
        String issueId = "1";

        // Call the service method
        issueService.deleteIssue(issueId);

        // Verify that the deleteById method was called
        verify(issueRepository, times(1)).deleteById(issueId);
    }
}

