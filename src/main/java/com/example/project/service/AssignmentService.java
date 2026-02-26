package com.example.project.service;

import com.example.project.entity.Assignment;
import com.example.project.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public Assignment saveAssignment(Assignment assignment) {
        // In a real application, you would add validation and business logic here.
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    /**
     * Business logic to get all assignments for a specific class.
     * This method encapsulates the repository call.
     *
     * @param classSubjectId The ID of the class.
     * @return A list of assignments.
     */
    public List<Assignment> getAssignmentsByClass(Long classSubjectId) {
        return assignmentRepository.findByClassSubjectId(classSubjectId);
    }
}