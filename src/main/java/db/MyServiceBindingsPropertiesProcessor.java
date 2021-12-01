package db;

import org.springframework.cloud.bindings.Binding;
import org.springframework.cloud.bindings.Bindings;
import org.springframework.cloud.bindings.boot.BindingsPropertiesProcessor;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

public class MyServiceBindingsPropertiesProcessor implements BindingsPropertiesProcessor {
    @Override
    public void process(Environment environment, Bindings bindings, Map<String, Object> map) {
        List<Binding> list = new Bindings().filterBindings("MySQL");
        if (list.size() > 0) {
            Map<String, String> secret = list.get(0).getSecret();
            System.out.println(secret);
        }

    }
}
