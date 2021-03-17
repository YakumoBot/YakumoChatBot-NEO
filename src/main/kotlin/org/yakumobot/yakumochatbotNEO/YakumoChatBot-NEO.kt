@file:Suppress("unused")
/*
*
* 插件基于Su1kaYCP前辈的
* ”parseeBot“进行开发(https://github.com/Su1kaYCP/parseeBot)


██╗   ██╗ █████╗ ██╗  ██╗██╗   ██╗███╗   ███╗ ██████╗  ██████╗██╗  ██╗ █████╗ ████████╗██████╗  ██████╗ ████████╗
╚██╗ ██╔╝██╔══██╗██║ ██╔╝██║   ██║████╗ ████║██╔═══██╗██╔════╝██║  ██║██╔══██╗╚══██╔══╝██╔══██╗██╔═══██╗╚══██╔══╝
 ╚████╔╝ ███████║█████╔╝ ██║   ██║██╔████╔██║██║   ██║██║     ███████║███████║   ██║   ██████╔╝██║   ██║   ██║
  ╚██╔╝  ██╔══██║██╔═██╗ ██║   ██║██║╚██╔╝██║██║   ██║██║     ██╔══██║██╔══██║   ██║   ██╔══██╗██║   ██║   ██║
   ██║   ██║  ██║██║  ██╗╚██████╔╝██║ ╚═╝ ██║╚██████╔╝╚██████╗██║  ██║██║  ██║   ██║   ██████╔╝╚██████╔╝   ██║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═════╝  ╚═════╝    ╚═╝


* 请遵循AGPL-3.0协议
* 你可以在:https://github.com/Zake-arias/YakumoChatBot/blob/main/LICENSE 找到该协议
* 本插件仅供学习交流之用
* 请勿将本插件用于一切商业性/非法用途
*/
package org.yakumobot.yakumochatbotNEO

import com.google.auto.service.AutoService
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.utils.info
import org.yakumobot.yakumochatbotNEO.BotMain.YCSetting.botname
import org.yakumobot.yakumochatbotNEO.BotMain.YCSetting.name
import org.yakumobot.yakumochatbotNEO.utils.WeatherAPI

@AutoService(JvmPlugin::class)
object BotMain : KotlinPlugin(
    JvmPluginDescription(
        "ltd.zake.YakumoChatBot-NEO",
        "1.0.0"
    )
) {
    override fun onEnable() {
        // 从数据库自动读取配置实例
        YCSetting.reload()
        YCListData.reload()
        YCExploreSet.reload()
        YCCommand.reload()
        YCInfos.reload()
        YCReplys.reload()
        YCItems.reload()

        logger.info { "Hi: $name" }
        logger.info {
            """
██╗   ██╗ █████╗ ██╗  ██╗██╗   ██╗███╗   ███╗ ██████╗  ██████╗██╗  ██╗ █████╗ ████████╗██████╗  ██████╗ ████████╗
╚██╗ ██╔╝██╔══██╗██║ ██╔╝██║   ██║████╗ ████║██╔═══██╗██╔════╝██║  ██║██╔══██╗╚══██╔══╝██╔══██╗██╔═══██╗╚══██╔══╝
 ╚████╔╝ ███████║█████╔╝ ██║   ██║██╔████╔██║██║   ██║██║     ███████║███████║   ██║   ██████╔╝██║   ██║   ██║   
  ╚██╔╝  ██╔══██║██╔═██╗ ██║   ██║██║╚██╔╝██║██║   ██║██║     ██╔══██║██╔══██║   ██║   ██╔══██╗██║   ██║   ██║   
   ██║   ██║  ██║██║  ██╗╚██████╔╝██║ ╚═╝ ██║╚██████╔╝╚██████╗██║  ██║██║  ██║   ██║   ██████╔╝╚██████╔╝   ██║   
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═════╝  ╚═════╝    ╚═╝   
                                                                                                                 
"""
        }
        logger.warning(
            "\n\n* 本插件仅供学习交流之用\n* 请勿将本插件用于一切商业性/非法用途\n\n"
        )
        subscribeGroupMessages {
            startsWith(YCCommand.help, removePrefix = true) {
                reply(
                    ("这里是${botname}bot哦! \n功能列表:" +
                            "\n1.对我说“<时间>分钟/小时后提醒我做<要做的事>”，可以创建提醒" +
                            "\n2. .r<骰子数：不超过100>d<面数：不超过1000>").trimMargin()
                )
            }

            startsWith(YCCommand.ping) {
                reply("pong,${botname}在哦")
            }

            startsWith(YCCommand.weather, removePrefix = true) {
                BotMain.launch {
                    try {
                        val weather = WeatherAPI()
                        val weatherList = weather.getWeather(weather.getCityId(it))
                        val reply = """
                            ${weatherList.city}今日天气:
                            天气${weatherList.weather}
                            气温:${weatherList.temperature}
                            ${weatherList.winddirection}风
                            风力${weatherList.windpower}级
                            空气湿度${weatherList.humidity}
                            [更新时间 ${weatherList.reporttime}]
                        """.trimIndent()
                        reply(reply)
                    } catch (e: Exception) {
                        reply("[错误]${e}")
                    }


                }
            }
        }
    }
    object YCCommand : AutoSavePluginConfig("YCCommand") {
        //日常功能:


        val sign by value(".签到")//TODO
        val help by value(".help")
        val ping by value("ping")
        val customReply by value(".自定义回复")
        val weather by value(".天气")
        val dice by value(".r")

        /*//文游功能:
        val explore by value(".探索")
        val exploreOver by value(".整理探索结果")
        val logon by value(".开始冒险")
        val info by value(".info")
        val type by value(".状态")
        val pvp by value(".pvp")*/

        //管理功能:
        val airing by value(".广播")
        val webServer by value(".server")

    }
    //Data
    object YCListData : AutoSavePluginData("YCData") {
        var signList: MutableList<Long> by value()
        var canExplore: MutableList<Long> by value()
        var deadList: MutableList<Long> by value()

        /* 可将 MutableMap<Long, Long> 映射到 MutableMap<Bot, Long>.
        val botToLongMap: MutableMap<Bot, Long> by value<MutableMap<Long, Long>>().mapKeys(Bot::getInstance,
            Bot::id)*/
        val playerCanLogon: MutableList<Long> by value()
    }

    object YCReplys : AutoSavePluginData("YCReplys") {
/*        var replys: MutableMap<String, String> by value()
        var reviewReplys: MutableMap<String, List> by value()*/
    }

    object YCSetting : AutoSavePluginConfig("YCSetting") {
        val name by value("test")
        val botname by value("莉莉白")
        val coldDown: Int by value(10)
        val weatherAppKey: String by value("")
        val webPort: Int by value(10024)
        var purviewList: MutableList<Long> by value()
        var webPurviewList: MutableMap<String, String> by value(mutableMapOf())
    }



    object YCInfos : AutoSavePluginConfig("YCInfo") {
        val newDays by value("新的一天开始啦")
    }

    object YCItems : AutoSavePluginConfig("YCItems") {
        val itemList: MutableMap<String, String> by value()
        val itemSet: MutableMap<String, String> by value()
    }

    object YCExploreSet : AutoSavePluginConfig("YCExploreSet") {
        val scenesNumber: Int by value(10)
        val scene: MutableMap<Int, String> by value()
        val sceneItem1: MutableMap<Int, String> by value()
        val sceneItem2: MutableMap<Int, String> by value()

        val botMoney: String by value("春点")
    }
}