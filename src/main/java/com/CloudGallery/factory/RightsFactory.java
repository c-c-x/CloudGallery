package com.CloudGallery.factory;

import com.CloudGallery.constants.RightsLvConstants;
import com.CloudGallery.model.Rights;
import com.CloudGallery.model.impl.LvOneRights;
import com.CloudGallery.model.impl.LvThreeRights;
import com.CloudGallery.model.impl.LvTwoRights;

/**
 * <p>
 * <h1>权益工厂类</h1>
 * <h2>根据用户等级返回对应的权益</h2>
 * </p>
 */
public class RightsFactory {

    private final long rightsLv;

    /**
     * <p>
     * 根据用户等级返回对应的权益
     * </p>
     *
     * @param rightsLv 用户等级
     */
    public RightsFactory(long rightsLv) {
        this.rightsLv = rightsLv;
    }

    public Rights getRights() {
        switch ((int) rightsLv) {
            case 1 -> {
                return new LvOneRights();
            }
            case 2 -> {
                return new LvTwoRights();
            }
            case 3 -> {
                return new LvThreeRights();
            }
            default -> {
                return null;
            }
        }
    }
}
