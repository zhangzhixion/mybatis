开发我的第一个MyBatis程序

1.资源目录I
    放在这个目录当中的，一般都是资源文件，配置文件.
    直接放到资源目录下的资源，等同于放到了类
    的根路径下。

2.开发步骤
★第一步，打包方式jar
★第二步:引入依赖
    mybatis依赖
    Mysql驱动依赖
★第三步:编写mybatis核心配置文件: mybatis- config. xmL

    注意：
    第一1这个文件名不是必须叫做mybatis-config.xml,可以用其他的名字。只是大家都采用这个名字。
    第二:这个文件存放的位置也不是固定的，可以随意，但一般情况下，会放到类的根路径下。|
    mybatis-config . xmL文件中的配置信息不理解没关系，先把连接效据库的信息修改以下即可。
    其他的别动。

    *第四步:编写XxxxMapper . xmL文件
    在这个配置文件当中编写SQL语句。
    这个文件名也不是固定的，放的位置也不是固定，我们这里给它起个名字，叫做: CarMapper ，xmL
    把它暂时放到类的根路径下。

    *第五步:在mybatis- config . xmL文件中指定XxxxMapper . xmL文件的路径:
    <mapper resource="CarMapper . xmL"/>
    注意: resource属性会自动从类的根路径下开始查找资源。

    *第六步:编写MyBatis程序。 (使用mybatis的类库，编写mybatis程序，连接数据库，做增删改查就行了。)
    在MyBatis当中，负责执行SQL语句的那个对象叫做什么呢?
    SqLSession
    SqLSession是专门用来执行SQL语句的，是一个Java程序和数据库之间的一次会话。
    要想获取SqLSession对象，需要先获取SqLSessionFactory对象，通过SqLSessionFactory工厂来生产SqLSession对象。
    怎么获取SqLSessionFactory对象呢?
    需要首先获取SqLSessionFactoryBuilder对象。
    通过SqLSessionFactoryBuilder对象的build方法，来获取一个SqLSessionFactory对象。
    mybatis的核心对象包括:
    SqLSessionFactoryBuilder
    SqLSessionFactory
    SqLSession
    SqLSessionFactoryBuilder --> SqLSessionFactory --> SqLSession


3.从xml中构建SqLSessionFactory
    通过官方的这句话，你能想到什么呢?
        第一1在MyBatis中 -定是有一个很重要的对象，这个对象是，SqLSessionFactory对象 。
        第二: SqLSessionFactory对 象的创建需要XML。
    XML是什么?
        它一定是一个配置文件。
4. mybatis中有 两个主要的配置文件;
    其中一个是，mybatis- config.xmL,这是核心配置文件，主要配置连接数据库的信息等。(一个)
    另一个是：XxxxMapper。这个文件是专门用来编写sql语句的配置文件。(一个表一个)
    t_ user表，一般会对应-个UserMapper . xmL
    t_ student表，- 般会对应一个StudentMapper . xmL
    学生表，一般会对应一个学生地图.XML

5.关于第一个程序的小细节
    ★mybatis中sqL语句的结尾”;"可以省略。
    Resources.getResourceAsStream
    小技巧:以后凡是遇到resource这个单词，大部分情况下，这种加载资源的方式就是从类的根路径下开始加载。(开始查找)
    优点:采用这种方式，从类路径当中加载资源，项目的移植性很强。项目从windows移植到linux,代码不需要修改，因为这个资源文件-一直都在 类路径当中。
        InputStream is = new FileInputStream("d: \ \mybatis-config. xmL");
        采用这种方式也可以。
    缺点:可移植性太差，程序不够健壮。可能会移植到其他的操作系统当中。导致以上路径无效，还需要修改java代码中的路径。这样违背了0CP原则。
    k已经验证了:
        mybatis核心配置文件的名字，不-定是: mybatis-config.xml. 可以是其它名字。
        mybatis核心配置文件存放的路径，也不-定 是在类的根路径下。可以放到其它位置。但为了项目的移植性，健壮性，最好将这个配置文件放到类路径下面。，
        InputStream is = CLassLoader . getSystemCLassLoader() . getResourceAsStream(" mybatis-config. xmL");
        CLassLoader . getSystemClassLoader()获取 系统的类加载器。
        系统类加载器有一一个 方法叫做: getResourceAsStream
        它就是从类路径当中加载资源的。
    通过源代码分析发现:
    InputStream is = Resources . getRe sourceAsStream(”mybatis-config .xmL");
    底层的源代码其实就是:
    InputStream is = ClassLoader . getSystemCLassLoader() . getResourceAsStream”mybatis-config . xmL");
★CarMapper . xmL文件的名字是固定的吗? CarMapper . xmL文件的路径是固定的吗?
都不是固定的。

6.关于mybatis的事务管理机制。 (深度剖析)
★在mybatis-config. xml文件中，可以通过以下的配置进行mybatis的事务管理
    <transactionManager type="JDBC"/>
★type属性的值包括两个:
    JDBC(jdbc)
    MANAGED (managed)
    type后面的值，只有以上两个值可选，不区分大小写。
★在mybatis中提供了两种事务管理机制:
    第一种: JDBC事务管理器
    第二种: MANAGED事务管理器
★JDBC事务管理器:
    mybatis框架自己管理事务，自已采用原生的JDBC代码去管理事务:
    conn . setAutoCommit(false);开启事务。
    业务处理...
    conn. commit();手动提交事务
    如果你编写的代码是下面的代码:
        SqLSession sqLSession = sqLSessionFactory 。openSession(true);
        表示没有开启事务。因为这种方式压根不会执行: conn . setAutoCommit(false);
        在JDBC事务中，没有执行conn . setAutoCommit(false);那么autoCommit就是true。
        如果autoCommit是true,就表示没有开启事务。只要执行任意-。 条DML语句就提交-。 次。
★MANAGED事 务管理器:
     mybatis不再负责事务的管理了。事务管理交给其它容器来负责。例如: spring.
    我不管事务了，你来负责吧。
    对于我们当前的单纯的只有mybatis的情况下，如果配置为: MANAGED
    那么事务这块是没人管的。没有人管理事务表示事务压根没有开启。
★JDBC中的事务:
    如果你没有在JDBC代码中执行: conn . setAutoCommit(faLse) ;的话，默认的autoCommit是true.

7.关于mybatis集成日志组件。让我们调试起来更加方便。
★mybatis常见的集成的日志组件有哪些呢?
    SLF4J (沙拉风) :沙拉风是-一个日志标准，其中有一个框架叫做Logback, 它实现了沙拉风规范。
    L0G4J
    L0G4J2
    STDOUT_ LOGGING
    ....
    注意: log4j Log4j2 Logback都是同一个作者开发的。
★其中STDOUT_ LOGGING是标准日志，mybatis已经实现了这种标准日志。mybatis框架本身已经实现了这种标准。
    只要开启即可。怎么开启呢?在mybatis- config . xmL文件中使用settings标签进行配置开启。
    <settings>
    <setting name="LogImpL" vaLue="STDOUT_ L0GGING"/>
    </settings>
    这个标签在编写的时候要注意，它应该出现在environments标签之前。注意顺序。当然，不需要记忆这个顺序。
    因为有dtd文件进行约束呢。我们只要参考dtd约束即可。
    这种实现也是可以的，可以看到一些信息，比如:连接对象什么时候创建，什么时候关闭，sqL语 句是怎样的。
    但是没有详细的日期，线程名字，等。如果你想使用更加丰富的配置，可以集成第三方的1og组件。
★集成logback日志框架。
    Logback日志框架实现了slf4j标准。(沙拉风:日志门面。日志标准。)
    第-步:引入Logback的依赖。
    <dependency>
        <groupId>ch . qos . Logback</ groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.11</version>
    </dependency>
    第二步:引入Logback所必 须的xmL配置文件。
    这个配置文件的名字必须叫做: Logback . xm1L或者logback- test.xml,不能是其它的名字。
    这个配置文件必须放到类的根路径下。不能是其他位置。




