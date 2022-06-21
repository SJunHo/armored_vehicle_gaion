package kr.gaion.armoredVehicle.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;

@Configuration
@Getter
@Setter
public class DBIndexConfig implements Serializable {
    private String index;
    private String readingType;
    private String clusterName;
    private String host;
    private Integer transportPort;
}