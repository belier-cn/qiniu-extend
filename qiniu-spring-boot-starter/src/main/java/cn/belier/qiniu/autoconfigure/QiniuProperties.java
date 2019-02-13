package cn.belier.qiniu.autoconfigure;

import cn.belier.qiniu.core.model.Bucket;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author H J
 * @date 2018/8/8
 */
@Data
@ConfigurationProperties("qiniu")
public class QiniuProperties {

    private String accessKey;

    private String secretKey;

    private List<Bucket> buckets = new ArrayList<>();

}
