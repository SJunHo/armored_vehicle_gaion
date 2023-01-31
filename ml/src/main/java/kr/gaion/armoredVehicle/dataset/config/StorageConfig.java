package kr.gaion.armoredVehicle.dataset.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageConfig {
    private String homeDir;
    private String dataDir;
    private String modelDir;
    private String modelIndexerDir;
    private String csvDelimiter;
    private String fileUploadDir;
    private String dbHost;
    private String dbPort;
    private String dbDatabase;
    private String dbUser;
    private String dbPassword;
}
