package com.giselladomizi.first.ui.main

import android.app.Activity

object ActivityCollector {
    private val activities = mutableListOf<Activity>()
    private const val MAX_STACK = 4 // máximo 4 activitys en el stack

    fun addActivity(activity: Activity) {
        if (activities.size >= MAX_STACK) {
            // eliminar la primera (la más antigua)
            val removed = activities.removeAt(0)
            removed.finish()
        }
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            activity.finish()
        }
        activities.clear()
    }
}