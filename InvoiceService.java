package com.Segovia.Lab_7.service;

import com.Segovia.Lab_7.model.Customer;
import com.Segovia.Lab_7.model.Invoice;
import com.Segovia.Lab_7.model.Product;
import com.Segovia.Lab_7.repository.CustomerRepository;
import com.Segovia.Lab_7.repository.InvoiceRepository;
import com.Segovia.Lab_7.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice, Long customerId, Set<Long> productIds) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        invoice.setCustomer(customer.get());
        invoice.setIssueDate(LocalDateTime.now());

        Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));
        double totalAmount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        invoice.setTotalAmount(totalAmount);
        invoice.setProducts(products);

        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> updateInvoice(Long id, Invoice invoiceDetails) {
        Optional<Invoice> existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice.isPresent()) {
            Invoice invoice = existingInvoice.get();
            invoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
            invoice.setTotalAmount(invoiceDetails.getTotalAmount());
            return Optional.of(invoiceRepository.save(invoice));
        }
        return Optional.empty();
    }

    public boolean deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }
}