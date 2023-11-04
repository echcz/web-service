# WEB 后端服务示例

本示例程序为一个 基于 Spring Boot 框架的 Web 后端项目。

## 使用的框架/工具

* Spring Boot: 依赖注入
* Spring MVC: WEB 框架
* JOOQ: ORM 框架
* Guava: 工具类库

## 程序架构/包结构

```yml
exception: # 自定义异常类，可由任何代码调用
  ServiceException...
util: # 通用工具类，抽取的通用逻辑，与业务无关，可由任何代码调用
  Constants
  ObjectUtil...
entity: # 业务实体层，包括实体、值对象，封装了业务对象及其不可变规则
  ActionLog...
usecase: # 用例层，封装了业务场景与用例（注意：应该一个用例只应该有一个职责），在 entity 层之下/外
  ActionLogUsecase...
  repository: # 仓库接口，持久化数据的接口/抽象
    ActionLogRepository...
  service: # 服务接口，外部/第三方服务的接口/抽象
    FileStorageService...
adapter: # 接口适配器层，内层用例层和外层基础设施/框架/驱动进行适配与数据转换，在 usecase 之下/外
  model: # 数据模型，适配器的参数与返回值对象
    PageVO...
  controller: # (入站适配器) WEB 控制器
    ActionLogController...
  handler: #（入站适配器）消息/事件处理器
    _...
  repository: # (出站适配器) 仓库(usecase.repository) 实现类
    JooqActionLogRepository...
  service: # (出站适配器) 服务(usecase.service) 实现类
    S3FileService...
  aspect: # AOP 切片
    ActionLogAspect...
config: # 应用配置层，对整个应用的依赖与行为进行配置，最底/外层(Main)
  bean: # 用于配置Bean的生成与依赖注入
    _...
  converter: # （请求参数）数据类型转换器
    StringToLocalDateTimeConverter...
  webfilter: # WEB 过滤器
    LogFilter...
  WebConfig...
  WebExceptionHandler...
WebApplication # 主类(Main Class)，最底/外层(Main)

# ------------- 文档、配置文件与脚本（非源代码） ---------------
pom.xml # Maven 构建定义
resource: # 资源文件，里面的文件将处理 classpath 下
  application.yml # 应用配置文件
sql: # SQL 脚本
  schema.sql # 表结构创建脚本
  init-data.sql # 初始化数据脚本
  changelog.sql # 表结构/数据变更日志脚本
.gitignore # Git 跟踪忽略文件定义
.dockerignore # Docker 镜像构建忽略文件定义
Dockerfile # Docker镜像构建脚本
docker-compose.yml # Docker Compose 容器编排脚本
k8s.yml # K8S容器编排脚本
Jenkinsfile # Jenkins 流水线定义脚本
qodana.yaml # 静态代码分析配置
README.md # 说明文档
```

> 本程序架构参考了[《架构整洁之道》--罗伯特 C. 马丁](https://book.douban.com/subject/30333919/)。
>
> 基于本书思想的实战教程可参考:
> [《干净架构最佳实践》--Jagger Wang](https://blog.jaggerwang.net/clean-architecture-in-practice/)、
> [《整洁架构实战：MVC 架构重构到整洁架构》--司鑫](https://www.jianshu.com/p/595b27818f2d)
> 

## 开发指南

* 尽量遵守 SOLID 原则，其说明可参考：[《细说 SOLID 原则》--杨珺](https://zhuanlan.zhihu.com/p/187516195)
* 依赖方向只能从 外/底层 到 内/高层，不能相反（可以使用依赖反转模式改变依赖方向）。
* `adapter` 可以直接依赖 `usecase.repository` 与 `usecase.service`，但如果是表达对业务用例的调用，则应该依赖 `Usecase` 类，而不是前两者。
* 应当先开发 内/高层，再开发 外/底层，如先开发 entity 层，再开发 usecase 层，最后再开发 adapter 层。
* 如果开发实体CRUD用例，请参考 `Document`、`DocumentRepository`、`DocumentUsecase`、`DocumentController` 及其相关类。
* 如果开发不可变数据添加与查询用例，请参考 `ActionLog`、`ActionLogRepository`、`ActionLogUsecase`、`ActionLogController` 及其相关类。
* 如果开发调用外部服务，请参考 `FileStorageService`、`S3FileStorageService`、`LocalFileStoreService`、`FileController` 及其相关类。
* 如果开发切片（AOP），请参考 `ActionLoggerAspect` 及其相关类。

## 运行

```shell
# 生成JOOQ代码，仅在修改数据库表结构后需要执行
mvn jooq-codegen:generate
# 运行
mvn clean spring-boot:run
# 打包
mvn clean package
# 构建Docker镜像
docker build -t echcz/web-service .
```
