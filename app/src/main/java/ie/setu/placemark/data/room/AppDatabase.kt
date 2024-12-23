package ie.setu.placemark.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.placemark.data.RunModel

@Database(entities = [RunModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRunDAO(): RunDAO
}
