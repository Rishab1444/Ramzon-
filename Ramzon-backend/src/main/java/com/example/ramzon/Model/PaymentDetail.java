package com.example.ramzon.Model;

import com.example.ramzon.RamEnums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetail {


    private String paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorPaymentLinkId;
    private String razorPaymentLinkReferenceId;
    private String razorPaymentLinkStatus;
    private String razorPaymentId;


}
