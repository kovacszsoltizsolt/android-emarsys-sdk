package com.emarsys.config

import com.emarsys.config.model.RemoteConfig
import com.emarsys.core.Mapper
import com.emarsys.core.Mockable
import com.emarsys.core.provider.random.RandomProvider
import com.emarsys.core.response.ResponseModel
import com.emarsys.core.util.log.LogLevel
import com.emarsys.core.util.log.Logger
import com.emarsys.core.util.log.entry.CrashLog
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.*

@Mockable
class RemoteConfigResponseMapper(private val randomProvider: RandomProvider) : Mapper<ResponseModel, RemoteConfig> {
    override fun map(responseModel: ResponseModel?): RemoteConfig {
        var remoteConfig = RemoteConfig()
        if (responseModel != null) {
            try {
                val jsonResponse = JSONObject(responseModel.body)
                if (jsonResponse.has("serviceUrls")) {
                    val serviceUrls = jsonResponse.getJSONObject("serviceUrls")
                    remoteConfig = remoteConfig.copy(
                            eventServiceUrl = validateUrl(serviceUrls.optString("eventService", null)),
                            clientServiceUrl = validateUrl(serviceUrls.optString("clientService", null)),
                            deepLinkServiceUrl = validateUrl(serviceUrls.optString("deepLinkService", null)),
                            inboxServiceUrl = validateUrl(serviceUrls.optString("inboxService", null)),
                            messageInboxServiceUrl = validateUrl(serviceUrls.optString("messageInboxService", null)),
                            mobileEngageV2ServiceUrl = validateUrl(serviceUrls.optString("mobileEngageV2Service", null)),
                            predictServiceUrl = validateUrl(serviceUrls.optString("predictService", null)))
                }
                remoteConfig = remoteConfig.copy(logLevel = calculateLogLevel(jsonResponse))
            } catch (jsonException: JSONException) {
                Logger.error(CrashLog(jsonException))
            }
        }
        return remoteConfig
    }

    private fun validateUrl(url: String?): String? {
        var validatedUrl: String? = null
        if (url != null) {
            val domain = URL(url).host
            if (domain.endsWith(".emarsys.net") || domain.endsWith(".emarsys.com")) {
                validatedUrl = url.toString()
            }
        }
        return validatedUrl
    }

    private fun calculateLogLevel(jsonResponse: JSONObject): LogLevel? {
        val luckyLogger = jsonResponse.optJSONObject("luckyLogger")
        var logLevelString = jsonResponse.optString("logLevel")

        if (luckyLogger != null) {
            val threshold = luckyLogger.getDouble("threshold")
            val randomValue = randomProvider.provideDouble(1.0)
            if (randomValue <= threshold && threshold > 0) {
                logLevelString = luckyLogger.getString("logLevel")
            }
        }

        return convertLogLevelStringToLogLevel(logLevelString)
    }

    private fun convertLogLevelStringToLogLevel(logLevel: String): LogLevel? {
        return when (logLevel.toLowerCase(Locale.ENGLISH)) {
            "trace" -> LogLevel.TRACE
            "debug" -> LogLevel.DEBUG
            "info" -> LogLevel.INFO
            "warn" -> LogLevel.WARN
            "error" -> LogLevel.ERROR
            else -> null
        }
    }
}