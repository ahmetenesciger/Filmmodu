package com.example.filmmodu.exstension

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

fun Fragment.navigateSafe(
    @IdRes resId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    try {
        findNavController().navigate(
            resId,
            bundle,
            navOptions,
            navigatorExtras
        )
    } catch (exp: Exception) {
        Log.e("FRAGMENT", exp.toString())
    }
}

fun launchNewActivity(context: Context, packageName: String) {
    var intent: Intent? = null
    intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent == null) {
        try {
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("market://details?id=$packageName")
            context.startActivity(intent)
        } catch (anfe: ActivityNotFoundException) {
            anfe.printStackTrace()
        }
    } else {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

fun launchNewActivity1(context: Context, packageName: String) {
    var intent: Intent? = context.packageManager.getLaunchIntentForPackage(packageName)

    if (intent == null) {
        val leanbackLaunchIntent: Intent? =
            context.packageManager.getLeanbackLaunchIntentForPackage(
                packageName
            )
        if (leanbackLaunchIntent != null) {
            context.startActivity(leanbackLaunchIntent)
        }
        else {
            try {
                intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data = Uri.parse("market://details?id=$packageName")
                context.startActivity(intent)
            } catch (anfe: ActivityNotFoundException) {
                anfe.printStackTrace()
            }
        }
    } else {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}


@SuppressLint("QueryPermissionsNeeded")
fun AppCompatImageView.setImage(context: Context, packageName: String) {
    val appsIntent = Intent(Intent.ACTION_MAIN, null)
    appsIntent.addCategory(Intent.CATEGORY_LAUNCHER)

    val localIntent1 = Intent(Intent.ACTION_MAIN, null)
    localIntent1.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)

    var appIcon: Drawable
    val apps =  context.packageManager.queryIntentActivities(appsIntent, 0)
    val localList1 = context.packageManager.queryIntentActivities(localIntent1, 0)
    apps.addAll(localList1)

    for (app in apps) {
        if (app.activityInfo.packageName == packageName) {
            val res: Resources = context.packageManager.getResourcesForApplication(
                app.activityInfo!!.applicationInfo
            )

            appIcon = res.getDrawableForDensity(
                app.iconResource,
                DisplayMetrics.DENSITY_XXXHIGH,
                null
            )!!
            try {
                this.setImageDrawable(appIcon)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }
    }
}

