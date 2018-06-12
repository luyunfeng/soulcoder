package tech.soulcoder.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yunfeng.lu on 2017/9/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAuto {
    public  String  value()  default "";
    public  ParamPrintOption outParamPrint() default ParamPrintOption.UNCONFIG;


    public enum ParamPrintOption{

        /**
         * 未配置
         */
        UNCONFIG("UNCONFIG","未配置"),

        /**
         * 打印
         */
        PRINT("PRINT","打印"),

        /**
         * 忽略打印
         */
        IGNORE("IGNORE","忽略打印");

        /**
         * 代码
         */
        private String code;

        /**
         * desc
         */
        private String desc;

        /**
         * @param code
         */
        private ParamPrintOption(String code,String desc) {
            this.code = code;
            this.desc = desc;
        }


    }
}
