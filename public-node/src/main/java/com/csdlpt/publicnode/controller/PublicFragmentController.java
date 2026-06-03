package com.csdlpt.publicnode.controller;

import com.csdlpt.publicnode.entity.PublicFragment;
import com.csdlpt.publicnode.repository.PublicFragmentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csdlpt.publicnode.utils.AESUtil;

import java.util.List;

import com.csdlpt.publicnode.dto.SecureFragmentDTO;
import com.csdlpt.publicnode.utils.AESUtil;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/public")
public class PublicFragmentController {

    private final PublicFragmentRepository repository;
    private final RestTemplate restTemplate;

    public PublicFragmentController(
            PublicFragmentRepository repository,
            RestTemplate restTemplate) {

        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public List<PublicFragment> getAll() {
        return repository.findAll();
    }

    @GetMapping("/encrypt-test")
    public String encryptTest() {
        String oid = "1";
        String encrypted = AESUtil.encrypt(oid);
        String decrypted = AESUtil.decrypt(encrypted);

        return "OID goc: " + oid
                + "<br>OID ma hoa: " + encrypted
                + "<br>OID giai ma: " + decrypted;
    }

    @GetMapping("/seed")
    public String seedData() {
        repository.deleteAll();

        repository.save(new PublicFragment(AESUtil.encrypt("1"), "Bought Laptop"));
        repository.save(new PublicFragment(AESUtil.encrypt("2"), "Bought Phone"));
        repository.save(new PublicFragment(AESUtil.encrypt("3"), "Bought Keyboard"));
        repository.save(new PublicFragment(AESUtil.encrypt("4"), "Bought Monitor"));
        repository.save(new PublicFragment(AESUtil.encrypt("5"), "Bought Mouse"));
        repository.save(new PublicFragment(AESUtil.encrypt("6"), "Bought Printer"));
        repository.save(new PublicFragment(AESUtil.encrypt("7"), "Bought Tablet"));
        repository.save(new PublicFragment(AESUtil.encrypt("8"), "Bought Smart Watch"));
        repository.save(new PublicFragment(AESUtil.encrypt("9"), "Bought Headphones"));
        repository.save(new PublicFragment(AESUtil.encrypt("10"), "Bought Camera"));
        repository.save(new PublicFragment(AESUtil.encrypt("11"), "Bought Gaming Chair"));
        repository.save(new PublicFragment(AESUtil.encrypt("12"), "Bought SSD"));
        repository.save(new PublicFragment(AESUtil.encrypt("13"), "Bought Graphics Card"));
        repository.save(new PublicFragment(AESUtil.encrypt("14"), "Bought Mechanical Keyboard"));
        repository.save(new PublicFragment(AESUtil.encrypt("15"), "Bought Microphone"));

        return "Seed public fragment successfully!";
    }

    @GetMapping("/customer")
    public String getCustomerByPurchase(@RequestParam String purchase) {

        List<PublicFragment> fragments = repository.findAll();

        for (PublicFragment fragment : fragments) {

            if (fragment.getPurchaseHistory()
                    .toLowerCase()
                    .contains(purchase.toLowerCase())) {

                String decryptedOid = AESUtil.decrypt(fragment.getEncOid());

                String url = "http://localhost:8082/secure/" + decryptedOid;

                try {
                    SecureFragmentDTO customer =
                            restTemplate.getForObject(url, SecureFragmentDTO.class);

                    return "Customer Name: "
                            + customer.getName()
                            + " | Purchase: "
                            + fragment.getPurchaseHistory();

                } catch (Exception e) {
                    return "Public data found: "
                            + fragment.getPurchaseHistory()
                            + "<br>But Secure Node is unavailable."
                            + "<br>Cannot retrieve customer identity.";
                }
            }
        }

        return "No customer found";
    }

    @GetMapping("/latency")
    public String measureLatency(
            @RequestParam String purchase) {

        long startTime = System.currentTimeMillis();

        List<PublicFragment> fragments = repository.findAll();

        for (PublicFragment fragment : fragments) {

            if (fragment.getPurchaseHistory()
                    .toLowerCase()
                    .contains(purchase.toLowerCase())) {

                try {

                    String decryptedOid =
                            AESUtil.decrypt(fragment.getEncOid());

                    String url =
                            "http://localhost:8082/secure/" + decryptedOid;

                    SecureFragmentDTO customer =
                            restTemplate.getForObject(
                                    url,
                                    SecureFragmentDTO.class
                            );

                    long endTime = System.currentTimeMillis();
                    long latency = endTime - startTime;

                    return "Customer: "
                            + customer.getName()
                            + "<br>Purchase: "
                            + fragment.getPurchaseHistory()
                            + "<br>Latency: "
                            + latency
                            + " ms";

                } catch (Exception e) {

                    long endTime = System.currentTimeMillis();
                    long latency = endTime - startTime;

                    return "Public data found: "
                            + fragment.getPurchaseHistory()
                            + "<br>But Secure Node is unavailable."
                            + "<br>Cannot retrieve customer identity."
                            + "<br>Latency before failure: "
                            + latency
                            + " ms";
                }
            }
        }

        return "No customer found";
    }
}