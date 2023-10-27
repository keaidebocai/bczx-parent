package top.woaibocai.bczx.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.common.log.service.AsyncOperLogService;
import top.woaibocai.bczx.mapper.SysOperLogMapper;
import top.woaibocai.bczx.model.entity.system.SysOperLog;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-27 13:57
 **/
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {
    @Resource
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
