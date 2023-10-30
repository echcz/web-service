package cn.echcz.webservice.usecase;

import cn.echcz.webservice.entity.Entity;
import cn.echcz.webservice.entity.User;
import cn.echcz.webservice.exception.DataDuplicateException;
import cn.echcz.webservice.usecase.repository.BaseRepository;
import cn.echcz.webservice.usecase.repository.DataUpdater;
import cn.echcz.webservice.usecase.repository.Querier;
import cn.echcz.webservice.usecase.repository.QueryFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntityUsecaseTests {
    Context context = new DefaultContext();
    @Mock
    ContextProvider contextProvider;
    @Mock
    BaseRepository<Integer, TestEntity, TestDataUpdater, TestQueryFilter> repository;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    AbstractEntityUsecase<Integer, TestEntity, TestDataUpdater, TestQueryFilter,
            BaseRepository<Integer, TestEntity, TestDataUpdater, TestQueryFilter>> usecase;

    private static class TestEntity implements Entity<Integer> {
        @Override
        public Integer getId() {
            return 1;
        }
    }

    private static class TestDataUpdater implements DataUpdater {
    }

    private static class TestQueryFilter implements QueryFilter<TestQueryFilter> {

        @Override
        public void and(Consumer<TestQueryFilter> filter) {
        }

        @Override
        public void or(Consumer<TestQueryFilter> filter) {
        }

        @Override
        public void not() {
        }
    }

    private static class TestQuerier implements Querier<TestDataUpdater, TestQueryFilter> {

        @Override
        public void filter(Consumer<TestQueryFilter> filter) {
            filter.accept(new TestQueryFilter());
        }

        @Override
        public Optional<TestDataUpdater> get() {
            return Optional.empty();
        }

        @Override
        public List<TestDataUpdater> list() {
            return Collections.emptyList();
        }
    }

    @BeforeEach
    void setup() {
        context.setCurrentUser(User.ANONYMOUS_USER);
        context.setTransactionId(UUID.randomUUID().toString());
        lenient().doReturn(context).when(contextProvider).getContext();
        lenient().doReturn(contextProvider).when(usecase).getContextProvider();
        lenient().doReturn(new TestQuerier()).when(repository).querier();
        lenient().doReturn(repository).when(usecase).getRepository();
    }

    @Test
    void shouldSuccessWhenContext() {
        Context context = usecase.context();
        assertThat(context).isEqualTo(this.context);
    }

    @Test
    void shouldSuccessWhenAdd() {
        doReturn(true).when(usecase).checkEntityForAdd(any());
        TestEntity entity = new TestEntity();
        usecase.add(entity);
        verify(usecase).checkEntityForAdd(entity);
        verify(repository).add(entity);
    }

    @Test
    void shouldFailWhenAdd() {
        doThrow(DataDuplicateException.class).when(usecase).checkEntityForAdd(any());
        Assertions.assertThrows(DataDuplicateException.class, () -> {
            usecase.add(new TestEntity());
        });
    }

    @Test
    void shouldSuccessWhenUpdate() {
        doReturn(true).when(usecase).checkEntityForUpdate(any());
        TestEntity entity = new TestEntity();
        usecase.update(entity);
        verify(usecase).checkEntityForUpdate(entity);
        verify(repository).update(any(), any());
    }

    @Test
    void shouldFailWhenUpdate() {
        doThrow(DataDuplicateException.class).when(usecase).checkEntityForUpdate(any());
        Assertions.assertThrows(DataDuplicateException.class, () -> {
            usecase.update(new TestEntity());
        });
    }

    @Test
    void shouldSuccessWhenDelete() {
        List<Integer> ids = List.of(1, 2);
        usecase.deleteByIds(ids);
        verify(repository).delete(any());
    }

    @Test
    void shouldSuccessWhenGet() {
        Integer id = 1;
        usecase.getById(id);
        verify(repository).querier();
    }

    @Test
    void shouldSuccessWhenList() {
        List<Integer> ids = List.of(1, 2);
        usecase.listByIds(ids);
        verify(repository).querier();
    }

    @Test
    void shouldSuccessWhenQuerier() {
        usecase.querier();
        verify(repository).querier();
    }
}
