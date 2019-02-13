package cn.belier.qiniu.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author belier
 * @date 2019/1/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UploadResult {

    /**
     * 可访问的url
     */
    private String url;

    /**
     * 带版本号的可访问url，解决CDN缓存问题
     */
    private String versionUrl;

    /**
     * 七牛云存储的key
     */
    private String key;

    /**
     * 带有版本号的key
     */
    private String versionKey;

    /**
     * 文件hash值
     */
    private String hash;

    /**
     * 桶的信息
     */
    private Bucket bucket;

}
