package cn.echcz.webservice.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页查询结果显示对象
 */
@Schema(description = "分页查询结果显示对象", accessMode = Schema.AccessMode.READ_ONLY)
public record PageVO<T>(
        @Schema(description = "数据条数")
        int count,
        @Schema(description = "数据")
        List<T> data
) {

    private static final PageVO EMPTY = new PageVO(0, Collections.EMPTY_LIST);
    /**
     * 空的分页对象
     */
    @SuppressWarnings("unchecked")
    public static <T> PageVO<T> empty() {
        return EMPTY;
    }

    /**
     * 转换分页查询结果
     */
    public <R> PageVO<R> map(Function<T, R> mapper) {
        List<R> rs = data.stream().map(mapper).toList();
        return new PageVO<>(count, rs);
    }
}
