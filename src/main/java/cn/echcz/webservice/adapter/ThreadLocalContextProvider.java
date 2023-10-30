package cn.echcz.webservice.adapter;

import cn.echcz.webservice.usecase.Context;
import cn.echcz.webservice.usecase.ContextProvider;
import cn.echcz.webservice.usecase.DefaultContext;
import org.springframework.stereotype.Component;

/**
 * 线程局部变量上下文持有者
 */
@Component
public class ThreadLocalContextProvider implements ContextProvider {
    private final ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

    @Override
    public Context getContext() {
        Context context = contextThreadLocal.get();
        if (context == null) {
            context = new DefaultContext();
            contextThreadLocal.set(context);
        }
        return context;
    }

    @Override
    public void clear() {
        contextThreadLocal.remove();
    }
}
