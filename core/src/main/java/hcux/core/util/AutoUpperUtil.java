package hcux.core.util;

import com.hand.hap.mybatis.entity.EntityField;
import com.hand.hap.system.dto.DTOClassInfo;
import hcux.core.annotation.AutoUpper;
import hcux.core.exception.HcuxException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

public class AutoUpperUtil {

    public static void autoUpper(Object target) throws HcuxException {

        if (target instanceof List) {
            // 如果是 List,则拆解,递归
            for (Object obj : (List) target) {
                autoUpper(obj);
            }
        } else {
            // 单个对象,则调用方法进行校验
            try {
                Class<?> dtoClazz = target.getClass();    //获取dto类
                Field[] fields = dtoClazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(AutoUpper.class)) {
                        if (field.getType().equals(String.class)) {
                            String value = (String) field.get(target);
                            if (StringUtils.isNotBlank(value)) {
                                field.set(target, value.toUpperCase());
                            }
                        }
                    }
                }
                // 取得所有带有@Children注解的Field
                for (EntityField f : DTOClassInfo.getChildrenFields(target.getClass())) {

                    Object children = PropertyUtils.getProperty(target, f.getName());
                    if (children != null) {
                        // 递归，处理@Children标记的对象
                        autoUpper(children);
                    }

                }
            } catch (Exception e) {
                throw new HcuxException("字段转大写失败，请联系系统管理员");
            }
        }

    }
}
