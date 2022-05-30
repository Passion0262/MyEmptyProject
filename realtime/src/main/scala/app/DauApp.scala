package app

import bean.DauInfo

import java.lang
import java.text.SimpleDateFormat
import java.util.Date
import com.alibaba.fastjson.{JSON, JSONObject}
import utils.{MyKafkaUtils, MyOffsetsUtils}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.Jedis

import scala.collection.mutable.ListBuffer
/**
  * Desc:  日活业务
  */
object DauApp {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("DauApp").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))
    val topic: String = "ods_base_log"
    val groupId: String = "ODS_BASE_LOG_GROUP"
//    val groupId: String = "gmall_dau_0523"


    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = MyKafkaUtils.getKafkaDStream(ssc, topic, groupId)
//    val offsets: Map[TopicPartition, Long] = MyOffsetsUtils.test(topic, groupId)
//    val kafkaDStream= MyKafkaUtils.getKafkaDStream(ssc, topic , groupId , offsets)
    kafkaDStream.print(1000)

//    val JSONObjectDStream: DStream[JSONObject] = kafkaDStream.map {
//      record => {
//        val jsonString: String = record.value()
//        //将json格式字符串转换为json对象
//        val jsonObject: JSONObject = JSON.parseObject(jsonString)
//        //从json对象中获取时间戳
//        val ts: lang.Long = jsonObject.getLong("ts")
//        //将时间戳转换为日期和小时  2020-10-21 16
//        val dateStr: String = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date(ts))
//        val dateStrArr: Array[String] = dateStr.split(" ")
//        var dt = dateStrArr(0)
//        var hr = dateStrArr(1)
//        jsonObject.put("dt", dt)
//        jsonObject.put("hr", hr)
//        jsonObject
//      }
//    }
//    JSONObjectDStream.print(1000)
//
//    val filteredDStream: DStream[JSONObject] = JSONObjectDStream.filter {
//      jsonObj => {
//        //获取登录日期
//        val dt = jsonObj.getString("dt")
//        //获取设备id
//        val mid = jsonObj.getJSONObject("common").getString("mid")
//        //拼接Redis中保存登录信息的key
//        var dauKey = "dau:" + dt
//        //获取Jedis客户端
//        val jedis: Jedis = MyRedisUtil.getJedisClient
//        //从redis中判断当前设置是否已经登录过
//        val isFirst: lang.Long = jedis.sadd(dauKey, mid)
//        //设置key的失效时间
//        if (jedis.ttl(dauKey) < 0) {
//          jedis.expire(dauKey, 3600 * 24)
//        }
//        //关闭连接
//        jedis.close()
//
//        if (isFirst == 1L) {
//          //说明是第一次登录
//          true
//        } else {
//          //说明今天已经登录过了
//          false
//        }
//      }
//    }

    ssc.start()
    ssc.awaitTermination()
  }
}