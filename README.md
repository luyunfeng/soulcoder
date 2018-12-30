https://gitee.com/fengyunlucode/soulcoder/  github 提交太卡了，还是用码云吧哈哈

# 使用

```
# git 拉去最新分支代码

git pull
git branch zzb
# 拷贝配置文件到指定目录
cp /www/wwwroot/zzb.soulcoder.tech/application.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application.yml
cp /www/wwwroot/zzb.soulcoder.tech/application-prod.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application-prod.yml
mvn clean
mvn package -Dmaven.test.skip=true
# 启动
cd /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/target/
nohup java -jar -Xms258m -Xmx258m soulcoder.jar &

```

# 配置文件

parent

```
<build>
        <plugins>
            <plugin>
                <!-- The plugin rewrites your manifest -->
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.3.0.RELEASE</version>
                <configuration><!-- 指定该Main Class为全局的唯一入口 -->
                    <mainClass>tech.soulcoder.StartApplication</mainClass>
                    <layout>ZIP</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal><!--可以把依赖的包都打包到生成的Jar包中-->
                        </goals>
                        <!--可以生成不含依赖包的不可执行Jar包-->
                        <!-- configuration>
                          <classifier>exec</classifier>
                        </configuration> -->
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

web

```
<build>
        <!-- 为jar包取名 -->
        <finalName>soulcoder</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.3.0.RELEASE</version>
            </plugin>
        </plugins>
    </build>
```
