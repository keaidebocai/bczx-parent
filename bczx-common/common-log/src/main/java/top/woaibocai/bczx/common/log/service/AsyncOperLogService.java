package top.woaibocai.bczx.common.log.service;

import top.woaibocai.bczx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog);
}
