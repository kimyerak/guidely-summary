package com.example.demo.domain.repository;

import com.example.demo.domain.entity.EndingCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndingCreditRepository extends JpaRepository<EndingCredit, Long> {
    
    List<EndingCredit> findByConversationId(Long conversationId);
    
    boolean existsByConversationId(Long conversationId);
} 