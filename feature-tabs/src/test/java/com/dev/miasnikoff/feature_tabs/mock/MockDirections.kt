package com.dev.miasnikoff.feature_tabs.mock

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections

class MockDirections : NavDirections {

    override val actionId: Int
        get() = 111
    override val arguments: Bundle
        get() = bundleOf()
}