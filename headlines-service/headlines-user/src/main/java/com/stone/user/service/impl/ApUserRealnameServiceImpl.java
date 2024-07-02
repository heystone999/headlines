package com.stone.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stone.apis.wemedia.IWemediaClient;
import com.stone.common.constants.UserConstants;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.user.dtos.AuthDto;
import com.stone.model.user.pojos.ApUser;
import com.stone.model.user.pojos.ApUserRealname;
import com.stone.model.wemedia.pojos.WmUser;
import com.stone.user.mapper.ApUserMapper;
import com.stone.user.mapper.ApUserRealnameMapper;
import com.stone.user.service.ApUserRealnameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    @Autowired
    private IWemediaClient wemediaClient;

    @Autowired
    private ApUserMapper apUserMapper;

    /**
     * 按照状态分页查询用户列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        dto.checkParam();

        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApUserRealname> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(ApUserRealname::getStatus, dto.getStatus());
        }
        page = page(page, lambdaQueryWrapper);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     * 更新审核结果
     *
     * @param dto
     * @param status 2审核失败    9审核成功
     * @return
     */
    @Override
    public ResponseResult updateStatus(AuthDto dto, Short status) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 修改实名认证状态
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setStatus(status);
        if (StringUtils.isNotBlank(dto.getMsg())) {
            apUserRealname.setReason(dto.getMsg());
        }
        updateById(apUserRealname);

        // 如果审核状态是9，就是成功，需要创建自媒体账户
        if (status.equals(UserConstants.PASS_AUTH)) {
            ResponseResult responseResult = createWmUserAndAuthor(dto);
            if (responseResult != null) {
                return responseResult;
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 创建自媒体账户
     *
     * @param dto
     * @return
     */
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {
        // 查询用户实名认证信息
        Integer userRealnameId = dto.getId();
        ApUserRealname apUserRealname = getById(userRealnameId);
        if (apUserRealname == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // 查询app端用户信息
        Integer userId = apUserRealname.getUserId();
        ApUser apUser = apUserMapper.selectById(userId);
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // 创建自媒体账户
        WmUser wmUser = wemediaClient.findWmUserByName(apUser.getName());
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setStatus(9);
            wemediaClient.saveWmUser(wmUser);
        }
        apUser.setFlag((short) 1);
        apUserMapper.updateById(apUser);

        return null;
    }
}
