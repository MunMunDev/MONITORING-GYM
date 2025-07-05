package com.abcd.monitoring_gym.utils

import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.ProgressModel

interface OnClickItem {
    interface ClickProgress{
        fun clickProgress(
            progress: ProgressModel
        )
    }

    interface ClickJenisPelatihan{
        fun clickJenisPelatihan(
            jenisPelatihan: JenisPelatihanModel
        )
    }

    interface ClickPelatihan{
        fun clickPelatihan(
            pelatihan: PelatihanModel
        )
    }

}