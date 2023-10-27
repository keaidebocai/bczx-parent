package top.woaibocai.bczx.common.log.aspect;

import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.woaibocai.bczx.common.log.annotation.Log;
import top.woaibocai.bczx.common.log.service.AsyncOperLogService;
import top.woaibocai.bczx.common.log.utils.LogUtil;
import top.woaibocai.bczx.model.entity.system.SysOperLog;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-27 12:12
 **/
@Aspect
@Component
public class LogAspect {
    @Resource
    private AsyncOperLogService asyncOperLogService;
    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint,
                                 Log sysLog){
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException(e);
        }
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }


}
