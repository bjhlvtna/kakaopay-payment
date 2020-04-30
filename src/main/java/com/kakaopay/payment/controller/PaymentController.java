package com.kakaopay.payment.controller;

import com.kakaopay.payment.dto.RequestDto;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping(path = "/api/v1/payments")
@RequestMapping(path = "/api/v1")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/payments/{id}", produces = "application/json")
    public Payment getPayment(@PathVariable String id) {
        return this.paymentService.findById(id).get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path= "/payments", consumes = "application/json; charset=UTF-8")
    public void createPayment(@RequestBody RequestDto.Payment payment) {
        this.paymentService.save(payment);
    }

    // update
    // TODO: path param & request body
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path= "/payments", consumes = "application/json; charset=UTF-8")
    public void updatePayment() {
    }

}
