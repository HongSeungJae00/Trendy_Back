package com.trendy.admin.stock;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStockService {
    
    private final AdminStockRepository stockRepository;
    private final AdminWarehouseRepository warehouseRepository;

    // 창고 목록 조회
    public List<AdminWarehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // 재고 저장
    public void saveStock(AdminStockDTO stockDTO) {
        AdminStock stock = new AdminStock();
        stock.setWarehouseId(stockDTO.getWarehouseId());
        stock.setProductId(stockDTO.getProductId());
        stock.setModelId(stockDTO.getModelId());
        stock.setStockQuantity(stockDTO.getStockQuantity());
        stock.setWarrantyGrade(stockDTO.getWarrantyGrade());
        stock.setBrand(stockDTO.getBrand());
        stock.setColor(stockDTO.getColor());
        stock.setSize(stockDTO.getSize());
        stock.setGender(stockDTO.getGender());
        
        stockRepository.save(stock);
    }

    // 재고 목록 조회
    public List<AdminStock> getAllStocks() {
        return stockRepository.findAll();
    }

    // 재고 상세 조회
    public AdminStock getStockById(Long stockId) {
        return stockRepository.findById(stockId)
            .orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    // 재고 수정
    public void updateStock(AdminStockDTO stockDTO) {
        AdminStock stock = stockRepository.findById(stockDTO.getStockId())
            .orElseThrow(() -> new RuntimeException("Stock not found"));
            
        stock.setWarehouseId(stockDTO.getWarehouseId());
        stock.setProductId(stockDTO.getProductId());
        stock.setModelId(stockDTO.getModelId());
        stock.setStockQuantity(stockDTO.getStockQuantity());
        stock.setWarrantyGrade(stockDTO.getWarrantyGrade());
        stock.setBrand(stockDTO.getBrand());
        stock.setColor(stockDTO.getColor());
        stock.setSize(stockDTO.getSize());
        stock.setGender(stockDTO.getGender());
        
        stockRepository.save(stock);
    }

    // 재고 삭제
    public void deleteStocks(List<Long> stockIds) {
        stockRepository.deleteAllById(stockIds);
    }
}