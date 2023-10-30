package cn.echcz.webservice.usecase;

/**
 * 上下文持有者
 */
public interface ContextProvider {
    /**
     * 获取上下文
     */
    Context getContext();

    /**
     * 清理上下文
     */
    void clear();
}
