package me.zeroest.zookeeper;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ZookeeperConfig {
    private static final String FILE_PATH = "application.yml";
    private static ZookeeperConfig instance;

    private final List<String> hosts = new ArrayList<>();

    public ZookeeperConfig(Collection<String> hosts) {
        this.hosts.addAll(hosts);
    }

    public String getConnectString() {
        return String.join(",", hosts);
    }

    public static ZookeeperConfig getConfig() {
        if (Objects.nonNull(instance)) {
            return instance;
        }

        synchronized (ZookeeperConfig.class) {
            if (Objects.nonNull(instance)) {
                return instance;
            }

            InputStream inputStream = ZookeeperConfig.class.getClassLoader().getResourceAsStream(FILE_PATH);

            if (Objects.isNull(inputStream)) {
                throw new IllegalStateException("Config file not exist");
            }

            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            List<String> hosts = (List<String>) config.get("zookeeper");
            instance = new ZookeeperConfig(hosts);
        }

        return instance;
    }
}
