package com.trendy.admin.sales;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminSalesService {
    
    private final AdminSalesRepository salesRepository;

        public List<AdminSalesDTO> getAllSales(String sortOrder) {
            List<AdminSales> salesList = salesRepository.findAll();
            return convertAndSort(salesList, sortOrder);
        }

        public List<AdminSalesDTO> searchSales(String keyword, String sortOrder) {
            List<AdminSales> salesList = salesRepository.findByModelIdContaining(keyword);
            return convertAndSort(salesList, sortOrder);
        }

        public void deleteSales(List<Long> saleIds) {
            saleIds.forEach(salesRepository::deleteById);
        }

        private List<AdminSalesDTO> convertAndSort(List<AdminSales> salesList, String sortOrder) {
            Stream<AdminSalesDTO> dtoStream = salesList.stream()
                    .map(this::convertToDTO);

            if ("asc".equals(sortOrder)) {
                return dtoStream.sorted((a, b) -> a.getTotalPrice().compareTo(b.getTotalPrice()))
                        .collect(Collectors.toList());
            } else {
                return dtoStream.sorted((a, b) -> b.getTotalPrice().compareTo(a.getTotalPrice()))
                        .collect(Collectors.toList());
            }
        }

        private AdminSalesDTO convertToDTO(AdminSales sales) {
            return AdminSalesDTO.builder()
                    .saleId(sales.getSaleId())
                    .modelId(sales.getModelId())
                    .quantity(sales.getQuantity())
                    .totalPrice(sales.getTotalPrice())
                    .build();
        }
    
}