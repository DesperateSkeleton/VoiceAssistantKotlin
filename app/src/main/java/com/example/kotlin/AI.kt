package com.example.kotlin

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.kotlin.APIRecipe.RecipeToString
import com.example.kotlin.Forecast.ForecastToString
import com.example.kotlin.numToStr.NumberToString
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern

object AI {
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(ParseException::class, IOException::class)
    fun getAnswer(question: String, activity: Activity, callback: Consumer<String?>) {
        var question = question
        val answer = arrayOfNulls<String>(1)
        question = question.toLowerCase()
        val map: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
        val mapDays: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.timeZone = TimeZone.getTimeZone("MSK")
        var numb: Int? = null
        val format = SimpleDateFormat("dd/MM/yyyy")
        try {
            numb = question.toInt()
        } catch (e: NumberFormatException) {
        }
        val pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)")
        var matcher = pattern.matcher(question)
        var datedate = ""
        if (matcher.find()) datedate = question.substring(matcher.start(), matcher.end())
        var diffDays: Long = 2212
        if (question.contains(activity.resources.getString(R.string.question_daysto).toLowerCase())) {
            val timeUp = format.parse(datedate).time
            val diff = -System.currentTimeMillis() + timeUp
            diffDays = diff / (24 * 60 * 60 * 1000)
        }
        mapDays["Mon"] = activity.resources.getString(R.string.wday_mon)
        mapDays["Tue"] = activity.resources.getString(R.string.wday_tue)
        mapDays["Wed"] = activity.resources.getString(R.string.wday_wed)
        mapDays["Thu"] = activity.resources.getString(R.string.wday_thu)
        mapDays["Fri"] = activity.resources.getString(R.string.wday_fri)
        mapDays["Sat"] = activity.resources.getString(R.string.wday_sat)
        mapDays["Sun"] = activity.resources.getString(R.string.wday_sun)
        map[activity.resources.getString(R.string.question_hello).toLowerCase()] = activity.resources.getString(R.string.answer_hello)
        map[activity.resources.getString(R.string.question_hay).toLowerCase()] = activity.resources.getString(R.string.answer_hay)
        map[activity.resources.getString(R.string.question_wayd).toLowerCase()] = activity.resources.getString(R.string.answer_wayd)
        map[activity.resources.getString(R.string.question_day).toLowerCase()] = calendar.time.toString().substring(8, 10)
        map[activity.resources.getString(R.string.question_hour).toLowerCase()] = calendar.time.toString().substring(11, 16)
        map[activity.resources.getString(R.string.question_wday).toLowerCase()] = mapDays[calendar.time.toString().substring(0, 3)]
        map[activity.resources.getString(R.string.question_daysto).toLowerCase()] = diffDays.toString()
        val cityPattern = Pattern.compile("weather in the city (\\p{L}+)", Pattern.CASE_INSENSITIVE)
        matcher = cityPattern.matcher(question)
        val numToStr1 = Pattern.compile(activity.resources.getString(R.string.question_buck).toLowerCase(), Pattern.CASE_INSENSITIVE)
        val matcherNum1 = numToStr1.matcher(question)
        val numToStr = Pattern.compile("(\\d+)", Pattern.CASE_INSENSITIVE)
        val matcherNum = numToStr.matcher(question)
        val RecipePattern = Pattern.compile("recipe: (\\p{L}+)", Pattern.CASE_INSENSITIVE)
        val matcherRecipe = RecipePattern.matcher(question)
        if (matcher.find()) {
            val cityName = matcher.group(1)
            ForecastToString.getForecast(cityName, object : Consumer<String?> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun accept(t: String?) {
                    map[activity.resources.getString(R.string.question_weather_city)] = t
                    answer[0] = map[activity.resources.getString(R.string.question_weather_city)] as String?
                    callback.accept(java.lang.String.join(", ", answer[0]))
                }
            })
        } else {
            if (matcherNum.find() && matcherNum1.find()) {
                val num = matcherNum.group(1)
                NumberToString.getNumber(num, object : Consumer<String?> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun accept(t: String?) {
                        map[activity.resources.getString(R.string.answer_number)] = t
                        answer[0] = map[activity.resources.getString(R.string.answer_number)] as String?
                        callback.accept(java.lang.String.join(", ", answer[0]))
                    }
                })
            } else
            {
                if (matcherRecipe.find())
                {
                    val RecipeName = matcherRecipe.group(1)
                    RecipeToString.getIngredients(RecipeName, object : Consumer<String?> {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun accept(t: String?) {
                            map[activity.resources.getString(R.string.question_recipe)] = t
                            answer[0] = map[activity.resources.getString(R.string.question_recipe)] as String?
                            callback.accept(java.lang.String.join(", ", answer[0]))
                        }
                    })
                }
                else
                {
                    if (map.containsKey(question))
                        answer[0] = map[question] as String?
                    else if (question.toLowerCase().contains(activity.resources.getString(R.string.question_daysto).toLowerCase()))
                        answer[0] = map[activity.resources.getString(R.string.question_daysto)] as String?;
                    else answer[0] = activity.resources.getString(R.string.answer_def)
                    if (answer[0] != null) callback.accept(java.lang.String.join(", ", answer[0]))
                }
            }
        }
    }
}