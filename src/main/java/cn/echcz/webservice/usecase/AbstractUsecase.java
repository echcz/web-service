package cn.echcz.webservice.usecase;

/**
 * 抽象的用例类
 */
public abstract class AbstractUsecase {
    protected abstract ContextProvider getContextProvider();

    /**
     * 获取上下文
     */
    protected Context context() {
        return getContextProvider().getContext();
    }
}
