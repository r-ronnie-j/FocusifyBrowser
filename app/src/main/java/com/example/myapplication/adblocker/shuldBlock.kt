package com.example.myapplication.adblocker

import android.util.Log
import com.example.myapplication.webkit.checkFilterList

fun shouldBlockAd(
    currentDomain: String,
    requestUrlString: String,
    isThirdPartyRequest: Boolean
): Boolean {
    try {


        val ultraListResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[4]
        )
        Log.d(
            "adblock",
            "ultralist ${ultraListResults} ${PopulateFilterListsCoroutine.combinedFilterLists[4].size}"
        )
        if (ultraListResults[0] == REQUEST_BLOCKED) {
            Log.d("adblock", "ultralist blocked ")
            return true
        }

        val ultraPrivacyResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[5]
        )

        if (ultraPrivacyResults[0] == REQUEST_BLOCKED) {
            Log.d("adblock", "ultralist privacy blocked ")

            return true
        } else if (ultraPrivacyResults[0] == REQUEST_ALLOWED) {
            return false
        }

        val easyListResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[0]
        )

        if (easyListResults[0] == REQUEST_BLOCKED) {
            Log.d("adblock", "easylist  blocked ")
            return true
        }

        val easyPrivacyResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[1]
        )

        if (easyPrivacyResults[0] == REQUEST_BLOCKED) {
            Log.d("adblock", "easylist privacy blocked ")
            return true
        }

        val fanboysAnnoyanceListResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[2]
        )

        if (fanboysAnnoyanceListResults[0] == REQUEST_BLOCKED) {
            Log.d("adblock", "fanboys privacy blocked ")
            return true
        }

        val fanboysSocialListResults = checkFilterList.checkFilterList(
            currentDomain,
            requestUrlString,
            isThirdPartyRequest,
            PopulateFilterListsCoroutine.combinedFilterLists[3]
        )

        return fanboysSocialListResults[0] == REQUEST_BLOCKED
    } catch (err: Exception) {
        return false
    }
}