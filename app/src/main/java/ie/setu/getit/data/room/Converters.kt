package ie.setu.getit.data.room

import androidx.room.TypeConverter
import ie.setu.getit.data.Category
import ie.setu.getit.data.ItemCondition
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromItemCondition(condition: ItemCondition): String = condition.name

    @TypeConverter
    fun toItemCondition(value: String): ItemCondition = ItemCondition.valueOf(value)


    @TypeConverter
    fun fromCategoryList(categories: List<Category>): String =
        categories.joinToString(",") { it.name }

    @TypeConverter
    fun toCategoryList(value: String): List<Category> =
        if (value.isEmpty()) emptyList()
        else value.split(",").map { Category.valueOf(it) }
}