package com.micro.controller;


import com.micro.config.KieService;
import com.micro.model.Address;
import com.micro.model.AddressCheckResult;
import com.micro.model.Applicant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@RestController
@Slf4j
@Data
@RefreshScope
public class TestController {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private KieService kieService;

    @Value("${app.resouceRoot}")
    private String home;

    @PostMapping("/testAddress")
    public Address testAddress(@RequestParam String code) {
        Address address = new Address();
        address.setPostcode(code);

        AddressCheckResult result = new AddressCheckResult();
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rule");
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        if (result.isPostCodeResult()) {

        } else {
            System.out.println("未通过");
        }
        return address;
    }

    @PostMapping("/address")
    public Address test(@RequestParam String code) {
        Address address = new Address();
        address.setPostcode(code);
        AddressCheckResult result = new AddressCheckResult();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        if (result.isPostCodeResult()) {

        } else {
            System.out.println("未通过");
        }
        return address;
    }

    @PostMapping("/ testKieService")
    public Applicant testKieService(@RequestParam String code) {
        Applicant applicant = new Applicant();
        applicant.setName(code);
        applicant.setAge(6);
        kieService.removePackage("stateless");
        kieService.addRule("stateless", "license");
        kieService.execute(applicant);
        kieService.removeRule("stateless", "Is of valid age");
        kieService.addRule("stateless", "license");
        kieService.addRule("stateless", "test");
        kieService.execute(applicant);
        return applicant;
    }

    @PostMapping("/test")
    public String test() {
        log.info(home);
        return home;
    }


}
