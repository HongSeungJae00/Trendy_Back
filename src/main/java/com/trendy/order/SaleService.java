package com.trendy.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long saleId) {
        return saleRepository.findById(saleId);
    }


    @Transactional
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public Sale updateSale(Long saleId, Sale updatedSale) {
    	return saleRepository.findById(saleId)
                .map(sale -> {
                    sale.setStatus(updatedSale.getStatus());
                    sale.setSettlementStatus(updatedSale.getSettlementStatus()); // 수정된 메서드 호출
                    sale.setUpdatedAt(updatedSale.getUpdatedAt()); // 수정된 메서드 호출
                    return saleRepository.save(sale);
                })
                .orElseThrow(() -> new RuntimeException("Sale not found with id " + saleId));
    }

    @Transactional
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }
    public int countSalesByStatus(Long userId, String status) {
        return saleRepository.countSalesByStatus(userId, status);
    }
    
    
}
