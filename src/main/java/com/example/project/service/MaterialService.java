package com.example.project.service;

import com.example.project.entity.Material;
import com.example.project.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Optional<Material> getMaterialById(Long id) {
        return materialRepository.findById(id);
    }

    public Material saveMaterial(Material material) {
        // Add any specific business logic for saving materials here.
        return materialRepository.save(material);
    }

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    /**
     * Business logic to get all materials for a specific class.
     *
     * @param classSubjectId The ID of the class.
     * @return A list of materials.
     */
    public List<Material> getMaterialsByClass(Long classSubjectId) {
        return materialRepository.findByClassSubjectId(classSubjectId);
    }
}