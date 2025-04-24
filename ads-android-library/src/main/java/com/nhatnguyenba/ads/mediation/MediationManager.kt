package com.nhatnguyenba.ads.mediation

import android.content.Context
import android.util.Log

/**
 * Quản lý mediation chain và điều phối việc load ad qua các adapter
 * Triển khai theo mẫu Singleton
 */
object MediationManager {

    private val TAG = "MediationManager"
    private val mediationChains = mutableMapOf<AdType, MediationAdapterChain>()

    // Các loại quảng cáo hỗ trợ
    enum class AdType {
        BANNER,
        INTERSTITIAL,
        REWARDED,
        NATIVE
    }

    /**
     * Thêm adapter vào chain cho từng loại ad
     * @param adType Loại quảng cáo
     * @param adapter Adapter cần thêm
     */
    fun addAdapter(adType: AdType, adapter: MediationAdapter) {
        val chain = mediationChains.getOrPut(adType) { MediationAdapterChain() }
        chain.addAdapter(adapter)
    }

    /**
     * Load ad với cơ chế failover
     * @param context Context
     * @param adType Loại quảng cáo
     * @param adUnitId ID quảng cáo
     * @param listener Callback nhận kết quả
     */
    fun loadAd(
        context: Context,
        adType: AdType,
        adUnitId: String,
        listener: MediationAdListener
    ) {
        val chain = mediationChains[adType] ?: run {
            listener.onAdFailed("No adapters configured for ${adType.name}")
            return
        }

        chain.loadAd(context, adUnitId, object : MediationAdListener {
            override fun onAdLoaded(adapter: MediationAdapter) {
                Log.d(TAG, "Ad loaded successfully by ${adapter::class.simpleName}")
                listener.onAdLoaded(adapter)
            }

            override fun onAdFailed(error: String) {
                Log.e(TAG, "All adapters failed: $error")
                listener.onAdFailed(error)
            }
        })
    }

    /**
     * Xử lý lifecycle cho tất cả adapters
     */
    fun onDestroy() {
        mediationChains.values.forEach { chain ->
            chain.destroy()
        }
    }
}

// Cập nhật MediationAdapterChain
class MediationAdapterChain : MediationAdapter {

    private val adapters = mutableListOf<MediationAdapter>()
    private var currentAdapterIndex = 0

    fun addAdapter(adapter: MediationAdapter): MediationAdapterChain {
        adapters.add(adapter)
        return this
    }

    override fun loadAd(
        context: Context,
        adUnitId: String,
        listener: MediationAdListener
    ) {
        if (adapters.isEmpty()) {
            listener.onAdFailed("No adapters available")
            return
        }

        loadNextAdapter(context, adUnitId, listener)
    }

    private fun loadNextAdapter(
        context: Context,
        adUnitId: String,
        listener: MediationAdListener
    ) {
        if (currentAdapterIndex >= adapters.size) {
            listener.onAdFailed("All adapters failed")
            return
        }

        val currentAdapter = adapters[currentAdapterIndex]
        currentAdapter.loadAd(context, adUnitId, object : MediationAdListener {
            override fun onAdLoaded(adapter: MediationAdapter) {
                listener.onAdLoaded(adapter)
            }

            override fun onAdFailed(error: String) {
                currentAdapterIndex++
                loadNextAdapter(context, adUnitId, listener)
            }
        })
    }

    override fun destroy() {
        adapters.forEach { it.destroy() }
    }
}

// Cập nhật MediationAdapter interface
interface MediationAdapter {
    fun loadAd(context: Context, adUnitId: String, listener: MediationAdListener)
    fun destroy()
}

interface MediationAdListener {
    fun onAdLoaded(adapter: MediationAdapter)
    fun onAdFailed(error: String)
}