package cn.belier.bean.handler.qiniu;

import cn.belier.bean.handler.purl.reflect.PrivateUrlField;
import cn.belier.bean.handler.purl.reflect.PrivateUrlOperation;
import cn.belier.qiniu.core.BucketInfoManager;
import cn.belier.qiniu.core.model.Bucket;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;

/**
 * @author belier
 * @date 2019/1/22
 */
public class QiniuPrivateUrlOperation implements PrivateUrlOperation {

    private final BucketInfoManager bucketInfoManager;

    private final Auth auth;

    public QiniuPrivateUrlOperation(BucketInfoManager bucketInfoManager, Auth auth) {
        this.bucketInfoManager = bucketInfoManager;
        this.auth = auth;
    }


    @Override
    public void handler(Object target, PrivateUrlField privateUrlField) {

        String fieldValue = (String) privateUrlField.getFieldValue(target);

        if (StringUtils.isNotBlank(fieldValue)) {

            Bucket bucket = bucketInfoManager.getBucket(privateUrlField.getPrivateUrl().value());

            if (bucket != null) {

                String url = bucket.getDomain() + fieldValue;

                privateUrlField.setFieldValue(target, auth.privateDownloadUrl(url));
            }


        }


    }
}
