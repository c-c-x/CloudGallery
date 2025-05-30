package com.CloudGallery.model.impl;

import com.CloudGallery.constants.RightsLvConstants;
import com.CloudGallery.model.Rights;

/**
 * 三级权限
 */
public class LvThreeRights implements Rights {
    @Override
    public long getMaxStorageSize() {
        return  RightsLvConstants.LV_THREE_MAX_STORAGE_SIZE;
    }

    @Override
    public long getMaxImageSize() {
        return  RightsLvConstants.LV_THREE_MAX_IMAGE_SIZE;
    }
}
