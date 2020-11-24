package com.example.practice_9

import android.content.Context
import androidx.room.*

// Сущность ученика
// Поля: фамилия, имя, отчество, пол, возраст.
@Entity(tableName = "Students")
class Student(val Family: String, val Name: String, val LastName: String, val Age: Int, val Gender: Char) {

    @PrimaryKey(autoGenerate = true)
    private var student_id: Long = 0

    fun getStudent_id(): Long? {
        return student_id
    }

    fun setStudent_id(student_id: Long) {
        this.student_id = student_id
    }

    val infoStudent: String
        get() = "id: ${getStudent_id()}) Family: $Family Name: $Name LastName: $LastName - $Age лет, пол $Gender"


}

@Dao
interface StudentsDao {
    @get:Query("SELECT * FROM students")
    val all: List<Student?>?

    @get:Query("SELECT * FROM students limit 1")
    val first: Student?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(vm: Student?)


    @Update
    fun updateNotification(notificationDetail: Student?)

    @Delete
    fun delete(vm: Student?)
}

@Database(entities = [Student::class], version = 1)
abstract class appDataBase : RoomDatabase() {
    abstract fun studentsDao(): StudentsDao?


    companion object {
        private const val DATABASE_NAME = "appDataBase"
        private var INSTANCE: appDataBase? = null

        // is migration really needed?


        fun getAppDatabase(context: Context): appDataBase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDataBase::class.java, DATABASE_NAME
                ) // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        //.fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}