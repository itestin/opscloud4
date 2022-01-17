package com.baiyi.opscloud.sshcore;

import com.baiyi.opscloud.domain.generator.opscloud.TerminalSession;

import javax.websocket.Session;

/**
 * @Author baiyi
 * @Date 2020/5/11 9:35 上午
 * @Version 1.0
 */
public interface ITerminalProcessor {

    void process(String message, Session session, TerminalSession terminalSession);

    String getState();

}