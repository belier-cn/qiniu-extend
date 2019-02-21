package cn.belier.qiniu.core.utils;

import cn.belier.qiniu.core.model.QiniuUploadModel;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author belier
 * @date 2018/11/15
 */
public class ExpireTimeUtils {

    private ExpireTimeUtils() {

    }

    public static long getSeconds(long expire, ChronoUnit chronoUnit) {

        if (expire <= 0) {
            return 3600L;
        }

        if (ChronoUnit.SECONDS.equals(chronoUnit)) {
            return expire;
        }

        return Duration.of(expire, chronoUnit).get(ChronoUnit.SECONDS);

    }

    public static long getSeconds(QiniuUploadModel uploadModel) {
        return getSeconds(uploadModel.getExpire(), uploadModel.getChronoUnit());
    }

}
