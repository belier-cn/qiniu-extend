package cn.belier.qiniu.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.time.temporal.ChronoUnit;

/**
 * @author belier
 * @date 2018/11/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QiniuUploadModel {

    /**
     * 桶名称
     */
    private String buckName;

    /**
     * 文件名称前缀
     */
    private String prefix;

    /**
     * 文件名称，为null时计算MD5值作为名称
     */
    private String name;

    /**
     * 输入流
     */
    private InputStream inputStream;

    /**
     * 文件类型
     */
    private String contentType;


    /**
     * 生成私有链接的有效时间 bucket 是私有空间才生效
     */
    private long expire = 3600L;


    /**
     * 生成私有链接的有效时间的单位
     */
    private ChronoUnit chronoUnit = ChronoUnit.SECONDS;

    /**
     * 是否压缩
     */
    private boolean compress = true;


}
