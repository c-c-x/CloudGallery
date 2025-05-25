package com.CloudGallery.service.impl;

import com.CloudGallery.domain.PO.LoginLog;

import com.CloudGallery.mapper.LoginLogMapper;

import com.CloudGallery.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;


@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}