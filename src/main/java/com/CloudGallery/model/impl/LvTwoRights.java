package com.CloudGallery.model.impl;

import com.CloudGallery.constants.RightsLvConstants;
import com.CloudGallery.model.Rights;

/**
 * 二级会员权限
 */
public class LvTwoRights implements Rights {
    @Override
    public long getMaxStorageSize() {
        return  RightsLvConstants.LV_TWO_MAX_STORAGE_SIZE;
    }

    @Override
    public long getMaxImageSize() {
        return  RightsLvConstants.LV_TWO_MAX_IMAGE_SIZE;
    }
}
