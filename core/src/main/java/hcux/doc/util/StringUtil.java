package hcux.doc.util;

import java.util.Map;

public class StringUtil {

    /**
     * 将bean里的string属性的字段值的特殊符号转义
     *
     * @param bean
     * @return
     */
    public static <T> void escapeBean(T bean) {

        Map<String, Object> objectMap = Map2BeanUtil.beanToMap(bean);
        objectMap.forEach((s, o) -> {
            if (o instanceof String) {
                String value = (String) o;
                value = value.replace("&", "&amp;");
                value = value.replace("<", "&lt;");
                value = value.replace(">", "&gt;");
                value = value.replace("\"", "&quot;");
                value = value.replace("'", "&apos;");
                objectMap.put(s, value);
            }
        });
        Map2BeanUtil.mapToBean(objectMap, bean);
    }
}
