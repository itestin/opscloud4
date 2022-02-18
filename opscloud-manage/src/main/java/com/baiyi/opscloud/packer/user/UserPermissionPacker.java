package com.baiyi.opscloud.packer.user;

import com.baiyi.opscloud.common.util.BeanCopierUtil;
import com.baiyi.opscloud.domain.generator.opscloud.UserPermission;
import com.baiyi.opscloud.domain.vo.user.UserPermissionVO;
import com.baiyi.opscloud.domain.vo.user.UserVO;
import com.baiyi.opscloud.service.user.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author baiyi
 * @Date 2021/5/27 4:12 下午
 * @Version 1.0
 */
@Component
@RequiredArgsConstructor
public class UserPermissionPacker {

    private final UserPermissionService permissionService;

    public void wrap(UserVO.IUserPermission iUserPermission) {
        UserPermission query = UserPermission.builder()
                .userId(iUserPermission.getUserId())
                .businessId(iUserPermission.getBusinessId())
                .businessType(iUserPermission.getBusinessType())
                .build();
        UserPermission userPermission = permissionService.getByUserPermission(query);
        if (userPermission != null)
            iUserPermission.setUserPermission(BeanCopierUtil.copyProperties(userPermission, UserPermissionVO.UserPermission.class));
    }

}
