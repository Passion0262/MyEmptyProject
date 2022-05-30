package utils

import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.{Properties, ResourceBundle}

/**
 * @author ：shadow
 * @date ：Created in 2022/4/20 15:26
 * 读取配置文件的工具类
 */
object MyPropsUtils {

  private val bundle: ResourceBundle = ResourceBundle.getBundle("config")

  def apply(propsKey: String) : String ={
    bundle.getString(propsKey)
  }

  def main(args: Array[String]): Unit = {
    println(MyPropsUtils("kafka.bootstrap-servers"))
  }

//  def main(args: Array[String]): Unit = {
//    val properties: Properties = MyPropsUtils.load("config.properties")
//    println(properties.getProperty("kafka.broker.list"))
//  }
//
//  def load(propertiesName:String): Properties ={
//    val prop=new Properties();
//    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName), StandardCharsets.UTF_8))
//    prop
//  }

}
