package com.application.hmkcopy.util.extentions

import com.application.hmkcopy.service.ErrorModel

typealias Callback = (() -> Unit)
typealias CallbackObject<T> = ((T) -> Unit)
typealias CallbackError = ((ErrorModel) -> Unit)
typealias CallbackObjects<T, B> = ((T, B) -> Unit)

typealias HMap = HashMap<String, Any?>