package top.woaibocai.bczx.common.exception;

import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-19 17:24
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(BoCaiException.class)
    public Result error(BoCaiException e){
        return Result.build(null, e.getResultCodeEnum());
    }

}
