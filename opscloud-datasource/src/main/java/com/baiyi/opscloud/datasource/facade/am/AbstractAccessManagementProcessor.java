package com.baiyi.opscloud.datasource.facade.am;

import com.baiyi.opscloud.core.InstanceHelper;
import com.baiyi.opscloud.core.factory.DsConfigHelper;
import com.baiyi.opscloud.datasource.facade.DsInstanceFacade;
import com.baiyi.opscloud.datasource.facade.UserAmFacade;
import com.baiyi.opscloud.datasource.facade.am.base.IAccessManagementProcessor;
import com.baiyi.opscloud.datasource.manager.base.NoticeManager;
import com.baiyi.opscloud.domain.constants.DsInstanceTagConstants;
import com.baiyi.opscloud.domain.generator.opscloud.User;
import com.baiyi.opscloud.domain.param.user.UserAmParam;
import com.baiyi.opscloud.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * @Author baiyi
 * @Date 2022/2/10 6:44 PM
 * @Version 1.0
 */
public abstract class AbstractAccessManagementProcessor implements IAccessManagementProcessor, InitializingBean {

    public final static boolean CREATE_LOGIN_PROFILE = true;

    @Resource
    protected UserService userService;

    @Resource
    protected DsInstanceFacade dsInstanceFacade;

    @Resource
    protected NoticeManager noticeManager;

    @Resource
    private StringEncryptor stringEncryptor;

    @Resource
    protected DsConfigHelper dsConfigHelper;

    @Resource
    private InstanceHelper instanceHelper;

    @Override
    public void createUser(UserAmParam.CreateUser createUser) {
        User user = userService.getByUsername(createUser.getUsername());
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(stringEncryptor.decrypt(user.getPassword()));
        }
        createUser(createUser.getInstanceUuid(), user);
    }

    abstract protected void createUser(String instanceUuid, User user);

    @Override
    public void afterPropertiesSet() throws Exception {
        UserAmFacade.register(this);
    }

    protected boolean enableMFA(String instanceUuid) {
        return instanceHelper.hasTagInInstance(instanceUuid, DsInstanceTagConstants.MFA.name());
    }

}