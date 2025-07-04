package com.abcd.monitoring_gym.utils

import com.abcd.monitoring_gym.data.model.ProgressModel

interface OnClickItem {
    interface ClickProgress{
        fun clickProgress(
            progress: ProgressModel
        )
    }

}