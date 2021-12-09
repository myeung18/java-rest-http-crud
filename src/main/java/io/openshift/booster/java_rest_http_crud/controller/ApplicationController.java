package io.openshift.booster.java_rest_http_crud.controller;

import io.openshift.booster.java_rest_http_crud.entity.Fruit;
import io.openshift.booster.java_rest_http_crud.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bindings.Binding;
import org.springframework.cloud.bindings.Bindings;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    @Resource
    FruitService fruitService;

    @GetMapping(value = "/fruits")
    public List<Fruit> getFruits() {
        return fruitService.findAll();
    }

    @GetMapping(value = "/fruits/{id}")
    public Fruit getFruit(@PathVariable String id) {
        return fruitService.findById(id);
    }

    @PostMapping(value = "/fruits")
    public Fruit createFruit(@RequestBody Fruit theFruit) {
        testPropertySource();
        testBindingLoad();
        System.out.println("ready to test");
        return fruitService.insertFruit(theFruit);
    }

    private void testBindingLoad() {
        List<Binding> bindings = new Bindings().filterBindings("postgresql");
        if (bindings.size() > 0) {
            Map<String, String> secret = bindings.get(0).getSecret();
            System.out.println("secret: " + secret);
        }//if
    }

    private void testPropertySource() {
        PropertySource<?> target = configurableApplicationContext.getEnvironment().getPropertySources().get("kubernetesServiceBindingSpecific");
        if (target == null) {
            System.out.println("failed to get ps: " + target);
            return;
        }

        if (target instanceof MapPropertySource) {
            Map<String, Object> map = ((MapPropertySource) target).getSource();

            System.out.println("list of propertieesy" + " " + map.size());
            for (String key : map.keySet()) {
                System.out.println(key + " , " + map.get(key));
            }
        } else {
            System.out.println("no list of propertieesy");
        }
//        System.out.println(bindingConfig.getPassword() + " , " + bindingConfig.getUsername());
    }

    @PutMapping(value = "/fruits/{id}")
    public Fruit updateFruit(@RequestBody Fruit theFruit, @PathVariable String id) throws IllegalArgumentException {
        return fruitService.updateFruit(id, theFruit);
    }

    @DeleteMapping(value = "/fruits/{id}")
    public void deleteFruit(@PathVariable String id) {
        fruitService.deleteFruit(id);
    }

}
