package com.trendy.mypage.resell;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendy.login.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesDataService {
    
    private final SalesDataRepository salesDataRepository;

    @Transactional
    public SalesData saveSalesData(SalesData salesData) {
        return salesDataRepository.save(salesData);
    }

    @Transactional(readOnly = true)
    public List<SalesData> findByUser(User user) {
        return salesDataRepository.findByUserId(user.getUserId());
    }

    @Transactional(readOnly = true)
    public Optional<SalesData> findById(Long id) {
        return salesDataRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<SalesData> findByModelId(String modelId) {
        return salesDataRepository.findByModelId(modelId);
    }

    @Transactional
    public void deleteSalesData(Long id) {
        salesDataRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SalesData> findAll() {
        return salesDataRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SalesData> findByProductOptionProductIdAndUserId(Long productId, Long userId) {
        return salesDataRepository.findByProductOptionProductIdAndUserId(productId, userId);
    }

    @Transactional
    public void deleteSalesData(SalesData salesData) {
        salesDataRepository.delete(salesData);
    }
} 