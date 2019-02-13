package cn.belier.qiniu.core;

import cn.belier.qiniu.core.model.Bucket;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author belier
 * @date 2019/1/16
 */
@Getter
public class BucketInfoManager {

    private List<Bucket> buckets;

    private Map<String, Bucket> bucketMap;

    public BucketInfoManager(List<Bucket> buckets) {

        this.bucketMap = new HashMap<>();

        if (buckets == null) {
            this.buckets = new ArrayList<>();
        } else {
            this.buckets = buckets;
            buckets.forEach(bucket -> bucketMap.put(bucket.getName(), bucket));
        }

    }

    public Bucket getBucket(String bucketName) {
        return bucketMap.get(bucketName);
    }

}
