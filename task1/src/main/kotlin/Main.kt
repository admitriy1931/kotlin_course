import kotlin.math.sqrt

fun main() {
    PushTask().main()
}
abstract class Push {
    var text: String? = null
    var type: String? = null

    companion object {
        internal const val TEXT_FIELD_NAME = "text"
        internal const val TYPE_FIELD_NAME = "type"
        internal const val GENDER_FIELD_NAME = "gender"
        internal const val AGE_FIELD_NAME = "age"
        internal const val RADIUS_FIELD_NAME = "radius"
        internal const val XCORD_FIELD_NAME = "x_coord"
        internal const val YCORD_FIELD_NAME = "y_coord"
        internal const val DATE_FIELD_NAME = "expiry_date"
        internal const val OSVERSION_FIELD_NAME = "os_version"
    }

    abstract fun newInstance(params: Map<String, String>): Push
    abstract fun filter(system: AppSystem): Boolean
}

class GenderPush() : Push(){
    var gender: String? = null

    override fun newInstance(params: Map<String, String>): GenderPush {
        val push = GenderPush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.gender = params[GENDER_FIELD_NAME]!!.toString()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return system.gender == gender
    }
}

class GenderAgePush() : Push(){
    var gender: String? = null
    var age: Int? = null

    override fun newInstance(params: Map<String, String>): GenderAgePush {
        val push = GenderAgePush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.gender = params[GENDER_FIELD_NAME]!!.toString()
        push.age = params[AGE_FIELD_NAME]!!.toInt()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return (system.gender == gender) && (system.age >= age!!)
    }
}

class LocationAgePush() : Push() {
    var xCord: Float? = null
    var yCord: Float? = null
    var radius: Int? = null
    var age: Int? = null

    override fun newInstance(params: Map<String, String>): LocationAgePush {
        val push = LocationAgePush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.radius = params[RADIUS_FIELD_NAME]!!.toInt()
        push.age = params[AGE_FIELD_NAME]!!.toInt()
        push.xCord = params[XCORD_FIELD_NAME]!!.toFloat()
        push.yCord = params[YCORD_FIELD_NAME]!!.toFloat()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return (system.age >= age!!) && (distanceFilter(system))
    }

    private fun distanceFilter(system: AppSystem): Boolean{
        val xDest: Float = system.xCord - xCord!!
        val yDest: Float = system.yCord - yCord!!
        return sqrt(xDest * xDest + yDest * yDest) <= radius!!
    }
}

class LocationPush() : Push() {
    var xCord: Float? = null
    var yCord: Float? = null
    var radius: Int? = null
    var date: Long? = null

    override fun newInstance(params: Map<String, String>): LocationPush {
        val push = LocationPush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.radius = params[RADIUS_FIELD_NAME]!!.toInt()
        push.date = params[DATE_FIELD_NAME]!!.toLong()
        push.xCord = params[XCORD_FIELD_NAME]!!.toFloat()
        push.yCord = params[YCORD_FIELD_NAME]!!.toFloat()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return (system.time <= date!!) && (distanceFilter(system))
    }

    private fun distanceFilter(system: AppSystem): Boolean{
        val xDest: Float = system.xCord - xCord!!
        val yDest: Float = system.yCord - yCord!!
        return sqrt(xDest * xDest + yDest * yDest) <= radius!!
    }
}

class TechPush() : Push() {
    var osVersion: Int? = null

    override fun newInstance(params: Map<String, String>): TechPush {
        val push = TechPush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.osVersion = params[OSVERSION_FIELD_NAME]!!.toInt()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return system.osVersion <= osVersion!!
    }
}

class AgeSpecificPush() : Push() {
    var age: Int? = null
    var date: Long? = null

    override fun newInstance(params: Map<String, String>): AgeSpecificPush {
        val push = AgeSpecificPush()
        push.text = params[TEXT_FIELD_NAME]!!.toString()
        push.type = params[TYPE_FIELD_NAME]!!.toString()
        push.age = params[AGE_FIELD_NAME]!!.toInt()
        push.date = params[DATE_FIELD_NAME]!!.toLong()
        return push
    }

    override fun filter(system: AppSystem): Boolean {
        return (system.time <= date!!) && (system.age >= age!!)
    }
}

data class AppSystem(
    val time: Long,
    val age: Int,
    val gender: String,
    val osVersion: Int,
    val xCord: Float,
    val yCord: Float
) {
    companion object {
        const val COUNT_PARAM: Int = 6
        fun newInstance(systemParams: Map<String, String>): AppSystem = AppSystem(
            time = systemParams["time"]!!.toLong(),
            age = systemParams["age"]!!.toInt(),
            gender = systemParams["gender"]!!.toString(),
            osVersion = systemParams["os_version"]!!.toInt(),
            xCord = systemParams["x_coord"]!!.toFloat(),
            yCord = systemParams["y_coord"]!!.toFloat()
        )
    }
}

class PushTask {
    val pushTypes: Map<String, Push> = mapOf(
        "GenderPush" to GenderPush(),
        "GenderAgePush" to GenderAgePush(),
        "LocationAgePush" to LocationAgePush(),
        "LocationPush" to LocationPush(),
        "TechPush" to TechPush(),
        "AgeSpecificPush" to AgeSpecificPush()

    )

    private fun getParam(countParam: Int): MutableMap<String, String> {
        val params: MutableMap<String, String> = mutableMapOf()
        for (i in 1..countParam){
            val param: List<String> = readLine().toString().split(" ")
            params[param[0]] = param[1]
        }
        return params
    }

    fun main(){
        val answer: MutableList<Push> = mutableListOf()
        val systemParam: MutableMap<String, String> = getParam(AppSystem.COUNT_PARAM)
        val appSystem: AppSystem = AppSystem.newInstance(systemParam)

        val countPush: Int = readLine()!!.toInt()
        for (i in 1..countPush){
            val countParam: Int = readLine().toString().toInt()
            val pushParam: MutableMap<String, String> = getParam(countParam)

            val nameType = pushParam["type"]
            val push = pushTypes[nameType]!!.newInstance(pushParam)

            if (push.filter(appSystem)){
                answer.add(push)
                println(push.text)
            }
        }

        if (answer.size == 0){
            println(-1)
        }
    }
}