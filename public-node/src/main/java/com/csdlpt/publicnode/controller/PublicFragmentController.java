package com.csdlpt.publicnode.controller;

import com.csdlpt.publicnode.dto.SecureFragmentDTO;
import com.csdlpt.publicnode.dto.SecureFragmentRequestDTO;
import com.csdlpt.publicnode.entity.PublicFragment;
import com.csdlpt.publicnode.original.CustomerData;
import com.csdlpt.publicnode.repository.PublicFragmentRepository;
import com.csdlpt.publicnode.utils.AESUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicFragmentController {

    private final PublicFragmentRepository repository;
    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;

    public PublicFragmentController(
            PublicFragmentRepository repository,
            RestTemplate restTemplate,
            JdbcTemplate jdbcTemplate) {

        this.repository = repository;
        this.restTemplate = restTemplate;
        this.jdbcTemplate = jdbcTemplate;
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

    @GetMapping("/fragment")
    public String verticalFragmentation() {

        List<CustomerData> customers = jdbcTemplate.query(
                "SELECT oid, name, ssn, credit_card, purchase_history FROM original_db.customer_data",
                (rs, rowNum) -> {
                    CustomerData customer = new CustomerData();
                    customer.setOid(rs.getInt("oid"));
                    customer.setName(rs.getString("name"));
                    customer.setSsn(rs.getString("ssn"));
                    customer.setCreditCard(rs.getString("credit_card"));
                    customer.setPurchaseHistory(rs.getString("purchase_history"));
                    return customer;
                }
        );

        repository.deleteAll();

        restTemplate.delete("http://localhost:8082/secure/clear");

        for (CustomerData customer : customers) {

            String encryptedOid =
                    AESUtil.encrypt(String.valueOf(customer.getOid()));

            PublicFragment publicFragment =
                    new PublicFragment(
                            encryptedOid,
                            customer.getPurchaseHistory()
                    );

            repository.save(publicFragment);

            SecureFragmentRequestDTO secureFragment =
                    new SecureFragmentRequestDTO(
                            customer.getOid(),
                            customer.getName(),
                            customer.getSsn(),
                            customer.getCreditCard()
                    );

            restTemplate.postForObject(
                    "http://localhost:8082/secure/save",
                    secureFragment,
                    SecureFragmentDTO.class
            );
        }

        return "Vertical fragmentation completed successfully!"
                + "<br>Total records fragmented: "
                + customers.size();
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