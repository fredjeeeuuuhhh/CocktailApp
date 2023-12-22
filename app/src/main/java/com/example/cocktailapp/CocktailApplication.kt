package com.example.cocktailapp

import android.app.Application
import com.example.cocktailapp.di.AppContainer
import com.example.cocktailapp.di.DefaultAppContainer

class CocktailApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer.AppContainerProvider.getAppContainer(context = applicationContext)
    }
}
