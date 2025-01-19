package org.easy.wallet.model

data class News(
  val title: String,
  val source: String,
  val language: String,
  val link: String,
  val time: String,
  val hash: String,
  val description: String,
  val tags: String
)