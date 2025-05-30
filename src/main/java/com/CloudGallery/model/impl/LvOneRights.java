package com.CloudGallery.model.impl;

import com.CloudGallery.constants.RightsLvConstants;
import com.CloudGallery.model.Rights;

/**
 * 一级会员权限
 */
public class LvOneRights implements Rights {
    @Override
    public long getMaxStorageSize() {
        return RightsLvConstants.LV_ONE_MAX_STORAGE_SIZE;
    }

    @Override
    public long getMaxImageSize() {
        return  RightsLvConstants.LV_ONE_MAX_IMAGE_SIZE;
    }
}
