package app

import bean.DauInfo

import java.lang
import java.text.SimpleDateFormat
import java.util.Date
import com.alibaba.fastjson.{JSON, JSONObject}
import utils.{MyKafkaUtil, MyRedisUtil}
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
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("DauApp")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))
    val topic: String = "gmall_start_bak"
    val groupId: String = "gmall_dau_0523"

    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = MyKafkaUtil.getKafkaStream(topic, ssc, groupId)
    val jsonDStream = kafkaDStream.map(_.value())
    jsonDStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}