package com.trendy.admin.user;

import lombok.Data;

@Data
public class AdminUserDTO {
    private Long userId;
    private String password;
    private String username;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String shippingAddress;
    private String receiptType;
    private String receiptCardNumber;
    private String bankName;
    private String accountNumber;
    private String accountHolder;
}
