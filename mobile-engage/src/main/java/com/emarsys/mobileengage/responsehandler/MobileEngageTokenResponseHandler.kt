package com.emarsys.mobileengage.responsehandler

import com.emarsys.core.Mockable
import com.emarsys.core.endpoint.ServiceEndpointProvider
import com.emarsys.core.request.model.RequestModel
import com.emarsys.core.response.AbstractResponseHandler
import com.emarsys.core.response.ResponseModel
import com.emarsys.core.storage.StringStorage
import com.emarsys.mobileengage.util.RequestModelUtils
import org.json.JSONException
import org.json.JSONObject

@Mockable
class MobileEngageTokenResponseHandler(private val tokenKey: String,
                                       private val tokenStorage: StringStorage,
                                       private val clientServiceProvider: ServiceEndpointProvider,
                                       private val eventServiceProvider: ServiceEndpointProvider,
                                       private val messageInboxServiceProvider: ServiceEndpointProvider) : AbstractResponseHandler() {

    override fun shouldHandleResponse(responseModel: ResponseModel): Boolean {
        val body = responseModel.parsedBody
        val request = responseModel.requestModel

        return isMobileEngage(request) && hasCorrectBody(body)
    }

    override fun handleResponse(responseModel: ResponseModel) {
        val body = responseModel.parsedBody
        try {
            tokenStorage.set(body.getString(tokenKey))
        } catch (ignore: JSONException) {
        }

    }

    private fun isMobileEngage(requestModel: RequestModel): Boolean {
        return RequestModelUtils.isMobileEngageV3Request(requestModel, clientServiceProvider, eventServiceProvider, messageInboxServiceProvider)
    }

    private fun hasCorrectBody(body: JSONObject?): Boolean {
        return body != null && body.has(tokenKey)
    }
}
