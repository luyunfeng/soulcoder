# git 拉去最新分支代码
git pull
git branch zzb
source /etc/profile
# 拷贝配置文件到指定目录
cp /www/wwwroot/zzb.soulcoder.tech/application.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application.yml
cp /www/wwwroot/zzb.soulcoder.tech/application-prod.yml /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/src/main/resources/application-prod.yml
mvn clean
mvn package -Dmaven.test.skip=true
# 启动
cd /www/wwwroot/zzb.soulcoder.tech/soulcoder/soulcoder-web/target/
nohup java -jar -Xms258m -Xmx258m soulcoder.jar &


