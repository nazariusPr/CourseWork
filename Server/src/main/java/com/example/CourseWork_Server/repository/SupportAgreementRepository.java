package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.SupportAgreement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportAgreementRepository extends JpaRepository<SupportAgreement, UUID> {}
