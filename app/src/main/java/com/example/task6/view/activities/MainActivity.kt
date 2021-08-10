package com.example.task6.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.task6.R
import com.example.task6.databinding.ActivityMainBinding

/*
Разработать стили и темы для интерфейса заметок: style.xml был сделан в Task3,
изменение темы - в SearchView Task7
Попробовать скачать заметку в виде файла из сети: был использован плагин JSON to Kotlin class;
тестовые заметки скачиваются с сайта jsonplaceholder
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val navController by lazy {
        Navigation.findNavController(this, binding.navHost.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}