package com.CloudGallery.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.common.utils.ListUtils;
import com.CloudGallery.common.utils.UserUtils;
import com.CloudGallery.constants.DictConstants;
import com.CloudGallery.constants.StatusConstants;
import com.CloudGallery.domain.PO.CgUserLv;
import com.CloudGallery.domain.PO.SysDictData;
import com.CloudGallery.domain.PO.User;
import com.CloudGallery.factory.RightsFactory;
import com.CloudGallery.mapper.CgRightsMapper;
import com.CloudGallery.mapper.CgUserLvMapper;
import com.CloudGallery.mapper.UserMapper;
import com.CloudGallery.model.Rights;
import com.CloudGallery.service.ICgUserLvService;
import com.CloudGallery.service.ISysDictDataService;
import com.CloudGallery.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户权益等级关联表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-05-27
 */
@Service
public class CgUserLvServiceImpl extends ServiceImpl<CgUserLvMapper, CgUserLv> implements ICgUserLvService {

    /**
     * 用户服务
     */
    @Resource
    private IUserService userService;

    /**
     * 字典数据服务
     */
    @Resource
    private ISysDictDataService dictDataService;

    /**
     * 用户等级mapper
     */
    @Resource
    private CgUserLvMapper userLvMapper;

    /**
     * 权益mapper
     */
    @Resource
    private CgRightsMapper rightsMapper;

    /**
     * 绑定等级
     *
     * @param userIds 用户id
     * @param lv      等级
     * @return 绑定结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> bindLv(List<Long> userIds, Long lv) {
        if (ListUtils.isEmpty(userIds) || ObjectUtil.isEmpty(lv)) {
            throw new CgServiceException("用户id和等级不能为空");
        }
        //查询用户是否都存在
        usersIsNotNull(userIds);
        //查看用户是否已经绑定相同等级
        List<CgUserLv> cgUserLvList = this.list(new LambdaQueryWrapper<CgUserLv>()
                .in(CgUserLv::getUserId, userIds)
                .eq(CgUserLv::getLv, lv)
                .eq(CgUserLv::getStatus, StatusConstants.YES_STATUS));
        if (ObjectUtil.isNotEmpty(cgUserLvList)){
            throw new CgServiceException("有用户已经绑定相同等级");
        }
        //查询等级是否存在
        boolean contains = dictDataService.list(new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getDictType, DictConstants.USER_LV)
                        .eq(SysDictData::getStatus, StatusConstants.YES_STATUS))
                .stream()
                .map(SysDictData::getValue)
                .toList()
                .contains(lv);
        if (!contains) {
            throw new CgServiceException("等级不存在");
        }
        //绑定等级
        List<CgUserLv> cgUserLvs = new ArrayList<>();
        userIds.forEach(userId -> {
            CgUserLv build = CgUserLv.builder()
                    .lv(lv)
                    .userId(userId)
                    .status(StatusConstants.YES_STATUS)
                    .createUser(UserUtils.getUserId())
                    .build();
            cgUserLvs.add(build);
        });
        userLvMapper.bindLv(cgUserLvs);
        //TODO 修改用户权益表
        //获取用户权益
        RightsFactory rightsFactory = new RightsFactory(lv);
        Rights rights = rightsFactory.getRights();
        return rightsMapper
                .bindRights(userIds, rights.getMaxImageSize(), rights.getMaxStorageSize(), UserUtils.getUserId()) ?
                Result.success() :
                Result.fail();
    }

    /**
     * 判断用户是否存在
     *
     * @param userIds 用户id
     */
    private void usersIsNotNull(List<Long> userIds) {
        List<User> userList = userService.list(new LambdaQueryWrapper<User>()
                .in(User::getId, userIds)
                .eq(User::getStatus, StatusConstants.YES_STATUS));
        if (ObjectUtil.isEmpty(userIds) && userList.size() != userIds.size()) {
            throw new CgServiceException("用户不存在");
        }
    }
}
