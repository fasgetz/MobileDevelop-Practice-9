package com.example.practice_9

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.students_add.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



class AddStudent : AppCompatActivity() {

    var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students_add)

        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        // доступ к элементам списка

        var Genders = ArrayList<String>()
        Genders.add("Мужской")
        Genders.add("Женский")


        val languages = Genders

        // Студент
        var abs = Student("Family", "Name", "LastName", 21, 'м')

        // доступ к счетчику

        val spinner = findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {

            val adapter = ArrayAdapter(this,

                    android.R.layout.simple_spinner_item, languages)

            spinner.adapter = adapter
        }

        val itemSelectedListener: OnItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                // Получаем выбранный объект
                selectedGender = parent.getItemAtPosition(position) as String

                val myToast = Toast.makeText(this@AddStudent, selectedGender, Toast.LENGTH_SHORT)
                myToast.show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.onItemSelectedListener = itemSelectedListener


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Обработчик кнопки добавления студента
    fun AddStudentDB(view: View?) {

        //val abs = 5
        val ageInput = age.text.toString().toIntOrNull()
        // Еслиз поля не пустые, то добавить студента
        if ( family.text.toString() != "" && name.text.toString() != "" && lastName.text.toString() != "" &&  ageInput != null && selectedGender != null ) {

            if (ageInput < 14) {
                val myToast = Toast.makeText(this@AddStudent, "Возраст студента должен быть > 14 лет", Toast.LENGTH_SHORT)
                myToast.show()
            }
            // Добавляем студента
            else {
                val myToast = Toast.makeText(this@AddStudent, "Добавляем в бд", Toast.LENGTH_SHORT)
                myToast.show()

                runBlocking {
                    launch(Dispatchers.Default) {
                        try {
                            val db = Room.databaseBuilder(
                                    applicationContext,
                                    appDataBase::class.java, "appDataBase"
                            ).build()

                            var dao = db?.studentsDao()

                            var gender: Char = 'n'
                            if (selectedGender == "Мужской")
                                gender = 'М'
                            else
                                gender = 'Ж'

                            var student = Student(family.text.toString(), name.text.toString(), lastName.text.toString(), age.text.toString().toInt(), gender)




                            dao?.insertNotification(student)

                            finish() // переходим назад
                        }
                        catch (ex: Exception) {
                            val myToast = Toast.makeText(this@AddStudent, ex.message, Toast.LENGTH_LONG)
                            myToast.show()
                        }
                    }
                }

            }

        }
        else {
            val myToast = Toast.makeText(this@AddStudent, "пустое значение", Toast.LENGTH_SHORT)
            myToast.show()
        }

    }
}