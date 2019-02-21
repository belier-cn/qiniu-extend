package cn.belier.qiniu.core.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author belier
 * @date 2018/11/2
 */
@Data
public class Bucket {

    private static final String OBLIQUE_LINE = "/";


    /**
     * 桶名称
     */
    private String name;

    /**
     * 域名
     */
    private String domain;

    /**
     * 是否私有
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isPrivate = false;


    public boolean isPrivate() {
        return this.isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }


    public void setDomain(String domain) {

        if (StringUtils.isNotBlank(domain)) {

            domain = domain.trim();

            if (!domain.endsWith(OBLIQUE_LINE)) {
                domain = domain + OBLIQUE_LINE;
            }
        }

        this.domain = domain;
    }
}
