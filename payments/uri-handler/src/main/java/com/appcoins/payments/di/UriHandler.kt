package com.appcoins.payments.di

import com.appcoins.uri_handler.PaymentScreenContentProvider
import com.appcoins.uri_handler.handler.UriHandler
import com.appcoins.uri_handler.handler.UriHandlerImpl

val Payments.uriHandler: UriHandler by lazyInit {
  UriHandlerImpl(
    oemIdExtractor = oemIdExtractor,
    oemPackageExtractor = oemPackageExtractor,
  )
}

var Payments.paymentScreenContentProvider: PaymentScreenContentProvider by lateInit()
