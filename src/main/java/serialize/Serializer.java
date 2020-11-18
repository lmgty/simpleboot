package serialize;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
public interface Serializer {

    /**
     * 序列化
     * @param object 待序列化对象
     * @return 字符数组
     */
    byte[] serialize(Object object);


    /**
     * 反序列化
     * @param bytes 待反序列化的字符数组
     * @param clazz 反序列化后的对象类型
     * @param <T> 反序列化后的对象类型
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes,Class<T> clazz);
}
