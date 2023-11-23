1.什么是CRUD
    C: Create增
    R: Retrieve查(检索)
    U: Update改
    D: DeLete删
2. insert
    <insert id=" insertCar">
        insert into t car(id, car_ num , brand , guide price , produce, time , car_ type)
        values(null, '1003','丰田霸道' ,30.0，'2000-10-11'，'燃油车');
    </insert>
   这样写的问题是?
   值显然是写死到配置文件中的。
   这个在实际开发中是不存在的。
   一定是前端的form表单提交过来数据。然后将值传给sqL语句。
    例如: JDBC的代码是怎么写的?
        String sqL = "insert into t car(id, car. num, brand, guide_ price, produce_ time , car_ type) values(nuLl,?,?,?,?,?)";
            ps. setString(1, xxx);
            ps. setString(2, yyy);
        在JDBC当中占位符采用的是?，在mybatis 当中是什么呢?
        和?等效的写法是: #{}
    在mybatis当中不能使用?占位符，必须使用#{}来代替JDBC当中的
    #{}和JDBC当中的?是等效的。

java程序中使用Map间以给SQL语句的占位符传值:
        Map<String, Object> map = new HashMap<>();
            map. put("k1", "1111");
            map . put("k2", "比 亚迪汉");
            map. put("k3", 10.0);
            map . put("k4", "2020-11-11");
            map . put("k5", "电车");
insert into t. car(id, car_ num, brand, guide_ price , produce_ time ，car_ type) vaLues(nulL, #{k1}, #{k2}, #{k3}, #{k4}, #{k5});
注意: #{这里写什么?写map集合的key,如果key不存在， 获取的是nuLL}
-般map集合的key起名的时候要见名知意。
        map . put(" carNum", "1111");
        map . put("brand", "比 亚迪汉2" );
        map . put("guidePrice", 10.0);
        map. put(" produceTime", "2020-11-11");
        map. put("carType"，"电车");
insert into t_ car(id, car_num , brand, guide_ price , produce_ time , car _type) values(null, #{carNum}，#{brand}，#{guidePrice} , #{produceTime}，#{carType});
java程序中使用P0J0类给SQL语句的占位符传值:
