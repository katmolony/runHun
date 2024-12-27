package ie.setu.placemark.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.UserProfileModel

@Database(entities = [RunModel::class, UserProfileModel::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRunDAO(): RunDAO
    abstract fun getUserProfileDAO(): UserProfileDAO
}