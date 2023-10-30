package cn.echcz.webservice.usecase.repository;

/**
 * 不更新数据的 数据更新器，
 * 标记接口
 */
public final class NoopDataUpdater implements DataUpdater {
    public static final NoopDataUpdater INSTANT = new NoopDataUpdater();

    private NoopDataUpdater() {
        // Singleton
    }
}
