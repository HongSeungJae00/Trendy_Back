package com.trendy.product;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SizeConverter implements AttributeConverter<ProductOption.Size, String> {

    @Override
    public String convertToDatabaseColumn(ProductOption.Size size) {
        return size != null ? size.getValue() : null;
    }

    @Override
    public ProductOption.Size convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        ProductOption.Size size = ProductOption.Size.fromValue(value);
        return size != null ? size : ProductOption.Size.SIZE_260;
    }
}
