package cn.belier.qiniu.autoconfigure;

import cn.belier.bean.handler.purl.reflect.PrivateUrlHandler;
import cn.belier.bean.handler.purl.reflect.PrivateUrlOperation;
import cn.belier.bean.handler.qiniu.QiniuPrivateUrlOperation;
import cn.belier.qiniu.core.BucketInfoManager;
import cn.belier.qiniu.core.QiniuUpload;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author belier
 * @date 2018/11/2
 */
@Configuration
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuAutoConfiguration {

    private final QiniuProperties qiniuProperties;

    @Autowired
    public QiniuAutoConfiguration(QiniuProperties qiniuProperties) {
        this.qiniuProperties = qiniuProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public com.qiniu.storage.Configuration configuration() {
        return new com.qiniu.storage.Configuration(Zone.autoZone());
    }

    @Bean
    @ConditionalOnMissingBean
    public Auth auth() {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    @Bean
    @ConditionalOnMissingBean
    public CdnManager cdnManager(Auth auth) {
        return new CdnManager(auth);
    }

    @Bean
    @ConditionalOnMissingBean
    public BucketManager bucketManager(Auth auth, com.qiniu.storage.Configuration configuration) {
        return new BucketManager(auth, configuration);
    }

    @Bean
    @ConditionalOnMissingBean
    public OperationManager operationManager(Auth auth, com.qiniu.storage.Configuration configuration) {
        return new OperationManager(auth, configuration);
    }

    @Bean
    @ConditionalOnMissingBean
    public UploadManager uploadManager(com.qiniu.storage.Configuration configuration) {
        return new UploadManager(configuration);
    }

    @Bean
    @ConditionalOnMissingBean
    public BucketInfoManager bucketInfoManager() {
        return new BucketInfoManager(qiniuProperties.getBuckets());
    }

    @Bean
    @ConditionalOnMissingBean
    public QiniuUpload qiniuUpload(Auth auth, BucketInfoManager bucketInfoManager, UploadManager uploadManager) {
        return new QiniuUpload(auth, bucketInfoManager, uploadManager);
    }

    @Bean
    @ConditionalOnClass({PrivateUrlHandler.class, PrivateUrlOperation.class, QiniuPrivateUrlOperation.class})
    @ConditionalOnMissingBean(QiniuPrivateUrlOperation.class)
    public QiniuPrivateUrlOperation qiniuPrivateUrlOperation(Auth auth, BucketInfoManager bucketInfoManager) {
        return new QiniuPrivateUrlOperation(bucketInfoManager, auth);
    }


}
