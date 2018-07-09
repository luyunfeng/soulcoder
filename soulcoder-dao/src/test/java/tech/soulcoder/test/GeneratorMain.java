package tech.soulcoder.test;

import org.mybatis.generator.api.ShellRunner;

import java.io.File;
import java.io.IOException;

/**
 * GeneratorMain.class
 *
 * @author yunfeng.lu
 * @date 2018/7/9
 */
public class GeneratorMain {
    // 本地 mysql jar包的路径 一般在你的 maven仓库里面找
    public static String mysqlJarPath = "/Applications/dev/maven_conf/repository/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar";
    // 这个只要放 相对路径就可以了
    public static String mapperXmlPath = "/soulcoder-dao/src/main/resources/tech/soulcoder/dao/mapper/generator";
    public static void main(String[] args) {
        args = new String[3];
        args[0] = "-configfile";
        GeneratorMain.class.getResource("/");
        String configRootPath = GeneratorMain.class.getResource("/").getPath();
        args[1] = configRootPath + "generator.xml";
        args[2] = "-overwrite";
        deleteXmlFiles();
        System.getProperties().put("mysql.driver.path",mysqlJarPath);
        ShellRunner.main(args);
    }

    // 删除原来的 文件 生成最新的
    public static void deleteXmlFiles() {
        try {
            File directory = new File("");
            String str = directory.getCanonicalPath() + mapperXmlPath;
            File file = new File(str);
            if (file.isDirectory() && null != file.listFiles() && file.listFiles().length > 0) {
                for (File f : file.listFiles()) {
                    f.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
