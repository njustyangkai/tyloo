package io.tyloo.dubbo.context;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import io.tyloo.api.Context.TylooContext;
import io.tyloo.api.Context.TylooContextLoader;
import io.tyloo.dubbo.constants.TransactionContextConstants;

import java.lang.reflect.Method;

/*
 * Dubbo 事务上下文编辑器实现
 *
 * 如果底层服务框架使用的是dubbo，可以设置TylooContextLoader为DubboTransactionContextEditor.class，使用dubbo隐式传参方式），
 * 通过 Dubbo 的隐式传参的方式，避免在 Dubbo Service 接口上声明 TransactionContext 参数，对接口产生一定的入侵
 * tyloo 通过 Dubbo Proxy 的机制，实现 `@Tyloo` 属性自动生成
 *
 * @Author:Zh1Cheung 945503088@qq.com
 * @Date: 15:32 2019/12/4
 *
 */
public class DubboTransactionContextLoader implements TylooContextLoader {
    @Override
    public TylooContext get(Object target, Method method, Object[] args) {

        //Dubbo隐式传参方式
        String context = RpcContext.getContext().getAttachment(TransactionContextConstants.TRANSACTION_CONTEXT);

        if (StringUtils.isNotEmpty(context)) {
            return JSON.parseObject(context, TylooContext.class);
        }

        return null;
    }

    @Override
    public void set(TylooContext transactionContext, Object target, Method method, Object[] args) {

        //Dubbo隐式传参方式
        RpcContext.getContext().setAttachment(TransactionContextConstants.TRANSACTION_CONTEXT, JSON.toJSONString(transactionContext));
    }
}
