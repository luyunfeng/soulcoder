source /etc/profile

echo "git 拉去最新分支代码"
git pull
git checkout zzb

echo "拷贝配置文件到指定目录"
cp /www/wwwroot/zzb.soulcoder.tech/application.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application.yml
cp /www/wwwroot/zzb.soulcoder.tech/application-prod.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application-prod.yml
echo "============maven clean============="
echo "============maven clean============="
echo "============maven clean============="
mvn clean
echo "============mvn package============="
echo "============mvn package============="
echo "============mvn package============="
mvn package -Dmaven.test.skip=true
echo "============开始启动============="
echo "============开始启动============="
echo "============开始启动============="
cd /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/target/
nohup java -jar -Xms258m -Xmx258m soulcoder.jar &
echo "============启动完成============="



