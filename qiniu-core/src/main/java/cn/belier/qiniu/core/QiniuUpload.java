package cn.belier.qiniu.core;

import cn.belier.qiniu.core.model.Bucket;
import cn.belier.qiniu.core.model.QiniuUploadModel;
import cn.belier.qiniu.core.model.UploadResult;
import cn.belier.qiniu.core.utils.ExpireTimeUtils;
import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author belier
 * @date 2018/11/5
 */
public class QiniuUpload {


    private final Auth auth;

    private final BucketInfoManager bucketInfoManager;

    private final UploadManager uploadManager;

    public QiniuUpload(Auth auth, BucketInfoManager bucketInfoManager, UploadManager uploadManager) {
        this.auth = auth;
        this.bucketInfoManager = bucketInfoManager;
        this.uploadManager = uploadManager;
    }


    /**
     * 上传文件
     *
     * @param uploadModel {@link QiniuUploadModel}
     * @return {@link UploadResult}
     * @throws IOException IO异常
     */
    public UploadResult upload(QiniuUploadModel uploadModel) throws IOException {

        // 对应桶对象
        Bucket bucket = bucketInfoManager.getBucket(uploadModel.getBuckName());

        // 生成token
        String token = auth.uploadToken(uploadModel.getBuckName());

        byte[] bytes = getBytes(uploadModel);

        // 上传图片
        Response response = uploadManager.put(bytes, getKey(uploadModel, bytes), token
                , null, uploadModel.getContentType(), false);

        // 解析响应
        UploadResult result = JSON.parseObject(response.bodyString(), UploadResult.class);

        long version = System.currentTimeMillis();

        // 设置带版本的key
        result.setVersionKey(result.getKey() + "?v=" + version);

        // 拼接url
        String url = bucket.getDomain() + result.getKey();

        if (bucket.isPrivate()) {
            url = auth.privateDownloadUrl(url, ExpireTimeUtils.getSeconds(uploadModel.getExpire(), uploadModel.getChronoUnit()));
        }

        String versionUrl = url.contains("?") ? url + "&v=" + version : url + "?v=" + version;

        return result.setUrl(url).setVersionUrl(versionUrl).setBucket(bucket);

    }

    /**
     * 获取字节数字
     *
     * @param uploadModel {@link QiniuUploadModel}
     * @return byte[]
     * @throws IOException IO异常
     */
    private byte[] getBytes(QiniuUploadModel uploadModel) throws IOException {

        // 压缩图片
        if (uploadModel.isCompress()) {

            // 判断是否是图片
            if (StringUtils.isNotBlank(uploadModel.getContentType())
                    && Arrays.stream(ImageIO.getWriterMIMETypes()).anyMatch(s -> s.equals(uploadModel.getContentType()))) {

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                // 原始尺寸压缩
                Thumbnails.of(uploadModel.getInputStream()).scale(1).toOutputStream(outputStream);

                return outputStream.toByteArray();
            }

        }

        return StreamUtils.copyToByteArray(uploadModel.getInputStream());
    }

    /**
     * 获取上传的key
     *
     * @param uploadModel {@link QiniuUpload}
     * @return key
     */
    private String getKey(QiniuUploadModel uploadModel, byte[] bytes) {

        if (StringUtils.isBlank(uploadModel.getPrefix())) {

            if (StringUtils.isBlank(uploadModel.getName())) {
                return null;
            } else {
                return uploadModel.getName();
            }

        } else {

            if (StringUtils.isBlank(uploadModel.getName())) {
                return uploadModel.getPrefix() + DigestUtils.md5Hex(bytes);
            } else {
                return uploadModel.getPrefix() + uploadModel.getName();
            }

        }

    }


}
