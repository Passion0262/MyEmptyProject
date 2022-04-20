package utils

import java.io.InputStreamReader
import java.util.Properties

/**
 * @author ：shadow
 * @date ：Created in 2022/4/20 15:26
 * 读取配置文件的工具类
 */
object MyPropertiesUtil {

  def main(args: Array[String]): Unit = {
    val properties: Properties = MyPropertiesUtil.load("config.properties")
    println(properties.getProperty("kafka.broker.list"))
  }

  def load(propertiesName:String): Properties ={
    val prop=new Properties();
    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName), "UTF-8"))
    prop
  }



}
