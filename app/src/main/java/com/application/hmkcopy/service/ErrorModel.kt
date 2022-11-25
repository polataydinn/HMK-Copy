package com.application.hmkcopy.service

import android.os.Parcelable
import com.application.hmkcopy.R
import com.application.hmkcopy.util.extentions.HMap
import com.application.hmkcopy.util.extentions.appString
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorModel(
    val _v: String? = null,
    val fault: FaultModel? = null,
    var customField: String? = null,
    var message: String? =  null
) : Parcelable {
    val displayMessage : String
        get() {
            return fault?.arguments?.statusMessage ?: fault?.message ?: appString(R.string.default_error_message)
        }
}

@Parcelize
data class FaultArgumentModel(
    val extensionPoint: String? = null,
    val statusCode: String? = null,
    val statusMessage: String? = null,
    val statusDetails: StatusDetailsModel? = null,
) : Parcelable

@Parcelize
data class StatusDetailsModel(
    val RETRY_AUTH: String? = null
) : Parcelable

@Parcelize
data class FaultModel(
    val arguments: FaultArgumentModel? = null,
    var message: String? = null,
    val type: String? = null,
) : Parcelable

fun createErrorModel(message: String, type: String? = null): ErrorModel {
    return ErrorModel(fault = FaultModel(message = message, type = type))
}

fun createRequestModel(type: String): HashMap<String, Any?> {
    val map = HMap()
    map["project"] = "Nike"
    map["type"] = type
    return map
}

enum class ErrorType(val id: String?) {
    AUTH_FAILED_EXCEPTION("AuthenticationFailedException"),
    UNKNOWN_HOST_EXCEPTION("UnknownHostException"),
    PAYMENT_EXCEPTION("PaymentException"),
    AUTH_ALREADY_MEMBER_EXCEPTION("LoginAlreadyInUseException"),
}