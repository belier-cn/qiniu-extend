package cn.belier.qiniu.core.utils;

import cn.belier.qiniu.core.model.QiniuUploadModel;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author belier
 * @date 2019/1/14
 */
public class QiniuUploadModelUtils {

    private QiniuUploadModelUtils() {
    }

    private static final String IMAGE = "image";

    /**
     * spring mvc 上传文件
     *
     * @param file 文件
     * @return url
     * @throws IOException IO异常
     */
    public static QiniuUploadModel getUploadModel(String bucketName, MultipartFile file) throws IOException {

        return QiniuUploadModel.builder()
                .buckName(bucketName)
                .inputStream(file.getInputStream())
                .contentType(file.getContentType()).build();

    }


    /**
     * 构造上传参数
     *
     * @param bufferedImage 图片
     * @return 七牛云图片url
     * @throws IOException IO异常
     */
    public static QiniuUploadModel getUploadModel(String bucketName, BufferedImage bufferedImage, MediaType mediaType) throws IOException, MimeTypeException {

        if (!IMAGE.equals(mediaType.getType())) {
            mediaType = MediaType.IMAGE_JPEG;
        }

        String formatName = MimeTypes.getDefaultMimeTypes()
                .forName(mediaType.toString()).getExtension().replace(".", "");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, formatName, outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return QiniuUploadModel.builder()
                .buckName(bucketName)
                .contentType(mediaType.toString())
                .inputStream(inputStream)
                .build();
    }

    /**
     * 上传图片
     *
     * @param bufferedImage 图片
     * @return 七牛云图片url
     * @throws IOException IO异常
     */
    public static QiniuUploadModel getUploadModel(String bucketName, BufferedImage bufferedImage) throws IOException, MimeTypeException {

        return getUploadModel(bucketName, bufferedImage, MediaType.IMAGE_JPEG);
    }


}
