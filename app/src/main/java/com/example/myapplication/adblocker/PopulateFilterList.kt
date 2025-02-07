package com.example.myapplication.adblocker

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object PopulateFilterListsCoroutine {

    val combinedFilterLists = ArrayList<ArrayList<List<Array<String>>>>()
    var completedLoading = false

    fun populateFilterLists(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val parseFilterListHelper = ParseFilterListHelper()
            withContext(Dispatchers.IO) {
                val easyList = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/easylist.txt"
                )
                Log.d("adblock", "easy list updated")
                val easyPrivacy = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/easyprivacy.txt"
                )
                Log.d("adblock", "easy list privacy updated")
                val fanboysAnnoyanceList = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/fanboy-annoyance.txt"
                )
                Log.d("adblock", "fan boy annoyace list")
                val fanboysSocialList = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/fanboy-social.txt"
                )
                Log.d("adblock", "fan boy social list")
                val ultraList = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/ultralist.txt"
                )
                Log.d("adblock", "ultra list")
                val ultraPrivacy = parseFilterListHelper.parseFilterList(
                    context.assets,
                    "filterlists/ultraprivacy.txt"
                )
                Log.d("adblock", "easyultra privacy list")
                combinedFilterLists.add(easyList)
                combinedFilterLists.add(easyPrivacy)
                combinedFilterLists.add(fanboysAnnoyanceList)
                combinedFilterLists.add(fanboysSocialList)
                combinedFilterLists.add(ultraList)
                combinedFilterLists.add(ultraPrivacy)
                completedLoading = true
            }
        }
    }
}
