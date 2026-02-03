package com.giselladomizi.first.ui.main

import android.app.Activity

object ActivityCollector {
    private val activities = mutableListOf<Activity>()
    private const val MAX_STACK = 4 // máximo 4 activities en el stack

    fun addActivity(activity: Activity) {
        if (activities.size >= MAX_STACK) {
            // elimina la primera (la más antigua)
            val removed = activities.removeAt(0)
            removed.finish()
        }
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }
}